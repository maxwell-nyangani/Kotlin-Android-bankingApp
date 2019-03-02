package com.nyanx.max.maxbankieren.adapters
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nyanx.max.maxbankieren.R
import com.nyanx.max.maxbankieren.models.AccountModel
import kotlinx.android.synthetic.main.li_account_list_item.view.*

class AccountListAdapter(
    private var dataList: List<AccountModel>,
    private var mContext: Context
) :RecyclerView.Adapter<AccountListAdapter.AccountViewHolder>(){

    interface AccountListItemEventHandlers{
        fun onAccountListItemClicked(view: View, position: Int)
        fun onAccountListItemLongClicked(view: View, position: Int)
    }
    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var accountTypeTxtVw:TextView = itemView.accountTypeTxtVw
        var accountProductNameTxtVw: TextView = itemView.accountProductNameTxtVw
        var accountNumberTxtVw: TextView = itemView.accountNumberTxtVw
        var accountIconImgVw: ImageView = itemView.accountIconImgVw
        var accountCurrencySymbolTxtVw: TextView = itemView.accountCurrencySymbolTxtVw
        var accountBalanceTxtVw: TextView = itemView.accountBalanceTxtVw
        var accountBalanceFractionPartTxtVw: TextView = itemView.accountBalanceFractionPartTxtVw
        var accountDecimalSymbolTxtVw: TextView = itemView.accountDecimalSymbolTxtVw
        var accountItemContainerCnstLyt: ConstraintLayout = itemView.accountItemContainerCnstLyt

    }
    private lateinit var callbacks: AccountListAdapter.AccountListItemEventHandlers


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AccountViewHolder {
        val itemView = LayoutInflater.from(p0.context)
            .inflate(R.layout.li_account_list_item, p0, false)

        return AccountViewHolder(itemView)    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        var accountModel: AccountModel = dataList.get(position)
        holder.accountTypeTxtVw.text="Credit Card"
        holder.accountItemContainerCnstLyt.setOnClickListener { v ->
            if(callbacks!=null){
                callbacks.onAccountListItemClicked(v,position)
            }
        }
    }

    fun setEventHandlers(callbacks: AccountListItemEventHandlers) {
        this.callbacks = callbacks
    }

}