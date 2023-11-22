package com.example.project

import android.os.Bundle
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
        val tv_seller : TextView = itemView.findViewById(R.id.tv_seller)
        val tv_title : TextView = itemView.findViewById(R.id.tv_title)
        val tv_explaination : TextView = itemView.findViewById(R.id.tv_explaination)
        val tv_sellingItem : TextView = itemView.findViewById(R.id.tv_sellingItem)
        val tv_price : TextView = itemView.findViewById(R.id.tv_price)
        val tv_status : TextView = itemView.findViewById(R.id.tv_status)

        init {
            itemView.setOnClickListener {
                val context = itemView.context
                // 전환할 프래그먼트의 인스턴스 생성
                val sellingPage = selling_page()

                // 전환을 위한 트랜잭션 시작
                val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()

                // 프래그먼트에 전달할 데이터 Bundle 생성
                val bundle = Bundle()
                bundle.putString("seller", tv_seller.text.toString())
                bundle.putString("title", tv_title.text.toString())
                bundle.putString("explaination", tv_explaination.text.toString())
                bundle.putString("sellingItem", tv_sellingItem.text.toString())
                bundle.putInt("price", tv_price.text.toString().toInt())
                bundle.putBoolean("status", tv_status.text.toString().toBoolean())

                // 데이터 Bundle을 프래그먼트에 전달
                sellingPage.arguments = bundle

                // 프래그먼트 전환
                transaction.replace(R.id.fragment, sellingPage)
                transaction.addToBackStack(null) // 이전 상태로 돌아가기 위해 스택에 추가
                transaction.commit()
            }

        }
    }
}
