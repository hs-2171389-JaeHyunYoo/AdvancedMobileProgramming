package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class listFragment : Fragment() {

    private val itemList = arrayListOf<item>()
    private var adapter: adapter = adapter(itemList)
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemCollectionRef = db.collection("items")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list, container, false)

        val logInWith = Firebase.auth.currentUser?.email.toString()

        // RecyclerView 설정
        val recyclerView = view.findViewById<RecyclerView>(R.id.showItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter 초기화
        adapter = adapter(itemList)
        recyclerView.adapter = adapter

        // Firestore에서 데이터 가져오기
        itemCollectionRef.get().addOnSuccessListener { result ->
            itemList.clear()
            for (document in result) {
                val seller = document.getString("seller") ?: "no seller"
                val title = document.getString("title") ?: ""
                val explaination = document.getString("explaination") ?: ""
                val sellingItem = document.getString("sellingItem") ?: ""
                val price = document.getLong("price")?.toInt() ?: 0
                val status = document.getBoolean("status") ?: false

                itemList.add(item(seller, title, explaination, sellingItem, price, status))
            }
            // 데이터 변경을 알려주기 위해 Adapter에 notifyDataSetChanged 호출
            adapter?.notifyDataSetChanged()
        }

        //필터링 기능
        val except = view.findViewById<CheckBox>(R.id.except)
        except.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                itemList.clear()
                val query: Query = itemCollectionRef.whereEqualTo("status", true)
                val task: Task<QuerySnapshot> = query.get()
                task.addOnSuccessListener { querySnapshot ->
                    val notForSale: MutableList<DocumentSnapshot> = querySnapshot.documents
                    for (document in notForSale) {
                        val seller = document.getString("seller") ?: "no seller"
                        val title = document.getString("title") ?: ""
                        val explanation = document.getString("explaination") ?: ""
                        val sellingItem = document.getString("sellingItem") ?: ""
                        val price = document.getLong("price")?.toInt() ?: 0
                        val status = document.getBoolean("status") ?: false
                        //println ("${title}?${explanation}?${sellingItem}?${price}?${status}")
                        itemList.add(
                            item(
                                seller,
                                title,
                                explanation,
                                sellingItem,
                                price,
                                status
                            )
                        )
                        adapter.notifyDataSetChanged()
                    }

                }
            }
            else {
                itemList.clear()

                //파이어스토어 DB에서 데이터들을 모두 가져와 ArrayList에 추가
                itemCollectionRef.get().addOnSuccessListener { result ->
                    for (document in result) {
                        val seller = document.getString("seller")?:""
                        val title = document.getString("title") ?: ""
                        val explanation = document.getString("explaination") ?: ""
                        val sellingItem = document.getString("sellingItem") ?: ""
                        val price = document.getLong("price")?.toInt() ?: 0
                        val status = document.getBoolean("status") ?: false

                        itemList.add(item(seller,title, explanation, sellingItem, price, status))

                        //println("${title}!${explanation}!${sellingItem}!${price}!${status}")
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }

        //아이템 추가 버튼
        //백 스택 쌓기 필요함
        val itemAddBtn = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        itemAddBtn.setOnClickListener {
            val moveToAdd = addItemFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, moveToAdd)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

}