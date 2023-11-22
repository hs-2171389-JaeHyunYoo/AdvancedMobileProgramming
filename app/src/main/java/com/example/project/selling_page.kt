package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

class selling_page : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.selling_page, container, false)

        val seller = arguments?.getString("seller", "")
        val title = arguments?.getString("title", "")
        val explaination = arguments?.getString("explaination", "")
        val sellingItem = arguments?.getString("sellingItem", "")
        val price = arguments?.getInt("price", 0)
        val status = arguments?.getBoolean("status", false)

        view.findViewById<TextView>(R.id.sellingSeller).text = "판매자 : " + seller
        view.findViewById<TextView>(R.id.sellingTitle).text = "글 제목 : " + title
        view.findViewById<TextView>(R.id.sellingExplaination).text = "판매 설명 : " + explaination
        view.findViewById<TextView>(R.id.sellingItemName).text = "판매 물품 : " + sellingItem
        view.findViewById<TextView>(R.id.sellingPrice).text = "판매 가격 : " + price.toString()
        if(status == true){
            view.findViewById<TextView>(R.id.sellingPageStatus).text = "판매 상태 : 판매 중"
        }
        else{
            view.findViewById<TextView>(R.id.sellingPageStatus).text = "판매 상태 : 판매 완료"
        }

        return view
    }
}