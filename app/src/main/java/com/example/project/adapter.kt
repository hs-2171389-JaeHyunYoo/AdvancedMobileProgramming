package com.example.project

import android.transition.Fade
import android.transition.Scene
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class adapter(val itemList: ArrayList<item>): RecyclerView.Adapter<adapter.ViewHolder>() {
    // (1) 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(view)
    }
    // (2) 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return itemList.size
    }
    // (3) View에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_seller.text = itemList[position].seller
        holder.tv_title.text = itemList[position].title
        holder.tv_explaination.text = itemList[position].explaination
        holder.tv_sellingItem.text = itemList[position].sellingItem
        holder.tv_price.text = itemList[position].price.toString()
        holder.tv_status.text = itemList[position].status.toString()

    }
    // (4) 레이아웃 내 View 연결
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        /*
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val number: TextView = itemView.findViewById(R.id.tv_number)
        */
        val tv_seller : TextView = itemView.findViewById(R.id.tv_seller)
        val tv_title : TextView = itemView.findViewById(R.id.tv_title)
        val tv_explaination : TextView = itemView.findViewById(R.id.tv_explaination)
        val tv_sellingItem : TextView = itemView.findViewById(R.id.tv_sellingItem)
        val tv_price : TextView = itemView.findViewById(R.id.tv_price)
        val tv_status : TextView = itemView.findViewById(R.id.tv_status)
        init{
            itemView.setOnClickListener {
                println(itemView)
                val activity = itemView.context as AppCompatActivity
                // 판매 페이지 Scene 생성
                val sellingpage : Scene = Scene.getSceneForLayout(sceneRoot, R.layout.selling_page, activity)
                // 판매 페이지로 전환
                TransitionManager.go(sellingpage)
                val home = Scene.getSceneForLayout(sceneRoot,R.layout.list,activity)

                val sellingPage = sellingpage.sceneRoot


                sellingPage.findViewById<TextView>(R.id.sellingSeller).text = tv_seller.text.toString()
                sellingPage.findViewById<TextView>(R.id.sellingTitle).text = tv_title.text.toString()
                sellingPage.findViewById<TextView>(R.id.sellingExplaination).text = tv_explaination.text.toString()
                sellingPage.findViewById<TextView>(R.id.sellingPrice).text = tv_price.text.toString()
                sellingPage.findViewById<TextView>(R.id.sellingPageStatus).text = tv_status.text.toString()


                val backBtn = activity.findViewById<Button>(R.id.sellingPageBack)
                backBtn.setOnClickListener {
                    println("백 버튼 눌림")
                    TransitionManager.go(home, Fade())
                }

            }
        }
    }

}