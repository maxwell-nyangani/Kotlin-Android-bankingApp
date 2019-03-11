package com.nyanx.max.maxbankieren.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.nyanx.max.maxbankieren.R
import com.nyanx.max.maxbankieren.adapters.AccountListAdapter
import com.nyanx.max.maxbankieren.models.AccountModel
import com.nyanx.max.maxbankieren.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_accounts.*
import kotlinx.android.synthetic.main.fragment_accounts.view.*
import java.util.ArrayList
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import com.nyanx.max.maxbankieren.CreditCardActivity
import android.support.v4.util.Pair
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AccountsFragment : Fragment() {

    interface AccountsFragmentEventHandlers{
        fun onLaunchAccountDetailsActivity(v:View,accountData:AccountModel)
    }
    private var accountsDataObservable:Observable<AccountModel> = Observable.fromArray()
    inner class AccountsFragmentUI(private  var layout:View) : AccountListAdapter.AccountListItemEventHandlers {
        override fun onAccountListItemClicked(view: View, position: Int) {
            gotoAccountDetails(view,dataList[position])
        }

        override fun onAccountListItemLongClicked(view: View, position: Int) {
            Log.d("+++++++++",position.toString())
        }

        private  val context:Context = layout.context
        private val recyclerOnScrollListener: EndlessRecyclerViewScrollListener
        private lateinit var adapter: AccountListAdapter
        private var dataList: MutableList<AccountModel> = ArrayList<AccountModel>()
        private lateinit var progressBar: ProgressBar
        private lateinit var accountsListBottomPgrssBr: ProgressBar
        private var currentPageNumber = 0
        private var maxPageNumber = 1
        private var totalListItemCount = 0
        init {
            dataList.add(AccountModel())
            dataList.add(AccountModel())
            dataList.add(AccountModel())
            dataList.add(AccountModel())
            adapter = AccountListAdapter(dataList, context)
            adapter.setEventHandlers(this)
            val llm = LinearLayoutManager(getContext())
            llm.orientation = LinearLayoutManager.VERTICAL
            layout.accountsListRclrVw.layoutManager = llm
            layout.accountsListRclrVw.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(
                layout.accountsListRclrVw.context,
                llm.orientation
            )
            layout.accountsListRclrVw.addItemDecoration(dividerItemDecoration)
            recyclerOnScrollListener = object : EndlessRecyclerViewScrollListener(llm) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    //Toast.makeText(context,"Page to fetch = "+String.valueOf(page),Toast.LENGTH_LONG).show();
                    if (page + 1 <= maxPageNumber) {
                        //deliveryNotificationsListBottomPgrssBr.setVisibility(View.VISIBLE)
                        //getDataFromAPI(page + 1)
                    }
                }
            }
            layout.accountsListRclrVw.addOnScrollListener(recyclerOnScrollListener)
        }

    }

    private val accountDetailsRequestCode: Int = 1

    private fun gotoAccountDetails(view: View, accountModel: AccountModel) {
        callbacks.onLaunchAccountDetailsActivity(view,accountModel)
    }

    private lateinit var ui:AccountsFragmentUI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var mView:View = inflater.inflate(R.layout.fragment_accounts, container, false)
        ui = AccountsFragmentUI(mView)
        return mView
    }

    private lateinit var callbacks: AccountsFragmentEventHandlers

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callbacks = context as AccountsFragmentEventHandlers
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement AccountsFragmentEventHandlers")
        }

    }

}
