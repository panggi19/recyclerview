package com.plete.addrecordapi

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListOfDataIntoRecyclerView()
        btnaja.setOnClickListener {

            addRecord()
        }
    }

    fun setupListOfDataIntoRecyclerView() {
        rvItemList.layoutManager = LinearLayoutManager(this)

        //ambil dataCEO
        var apiInterface: ApiInterface =
            ApiClient().getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getCEOs().enqueue(object : Callback<ArrayList<CEOModel>> {
            override fun onFailure(call: Call<ArrayList<CEOModel>>?, t: Throwable) {
                Toast.makeText(baseContext, "Data Downloading is failed", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ArrayList<CEOModel>>?,
                response: Response<ArrayList<CEOModel>>
            ) {
                var ceoData = response?.body()!!
                if (ceoData.size > 0) {
                    rvItemList.visibility = View.VISIBLE
                    tvNoRecordAvailable.visibility = View.GONE
                    rvItemList.adapter = MyAdapter(this@MainActivity, ceoData)
                } else {
                    rvItemList.visibility = View.GONE
                    tvNoRecordAvailable.visibility = View.VISIBLE
                }
                Toast.makeText(baseContext, "Data Downloading is Successfully", Toast.LENGTH_LONG)
                    .show()
            }
        })
        /**
         * Method to ad data
         */

    }

    fun addRecord() {
        val name = etName.text.toString()
        val companyName = etCName.text.toString()

        if (name == "" || companyName == "") {
            Toast.makeText(
                this,
                "masih ada field yang kosong, tolong dilengakpi",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val newCEO: CEOModel = CEOModel(null, name, companyName)

            var apiInterface: ApiInterface =
                ApiClient().getApiClient()!!.create(ApiInterface::class.java)
            var requestCall: Call<CEOModel> = apiInterface.addCEO(newCEO)

            requestCall.enqueue(object : Callback<CEOModel> {
                override fun onFailure(call: Call<CEOModel>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "gagal tersimpan", Toast.LENGTH_LONG
                    ).show()


                }

                override fun onResponse(call: Call<CEOModel>, response: Response<CEOModel>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@MainActivity,
                            "berhasil tersimpan", Toast.LENGTH_LONG
                        ).show()
                        setupListOfDataIntoRecyclerView()
                        etName.setText("")
                        etCName.setText("")
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "gagal tersimpan", Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })

        }
    }

    fun deleteRecodDialog(CEOModel: CEOModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("DElete record")

        builder.setMessage("Are You Sure?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("yes") { dialog, which: Int ->

            var apiInterface: ApiInterface =
                ApiClient().getApiClient()!!.create(ApiInterface::class.java)


            var requestCall: Call<CEOModel> = apiInterface.deleteCEO(CEOModel.id!!)



            requestCall.enqueue(object : Callback<CEOModel> {
                override fun onFailure(call: Call<CEOModel>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "gagal Terhapus", Toast.LENGTH_LONG
                    ).show()


                }

                override fun onResponse(call: Call<CEOModel>, response: Response<CEOModel>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@MainActivity,
                            "berhasil tersimpan", Toast.LENGTH_LONG
                        ).show()
                        setupListOfDataIntoRecyclerView()
                        etName.setText("")
                        etCName.setText("")
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "gagal tersimpan", Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })

        }


        builder.setNegativeButton("NO") { dialog, which: Int ->
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    /**
     * method  to custom dialog
     */

    fun updateRecordDialog(CEOModel: CEOModel) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        updateDialog.setContentView(R.layout.dialog_update)


        updateDialog.etUpdateName.setText(CEOModel.name)
        updateDialog.etUpdateCompany.setText(CEOModel.company_name)


        updateDialog.tvUpdate.setOnClickListener {
            val name = updateDialog.etUpdateName.text.toString()
            val companyName = updateDialog.etUpdateCompany.text.toString()


            if (name.isEmpty() && companyName.isEmpty()) {
                Toast.makeText(this, "masih ada field yang kosong", Toast.LENGTH_LONG).show()
            } else {
                val newCEO: CEOModel = CEOModel(null, name, companyName)


                var apiInterface: ApiInterface =
                    ApiClient().getApiClient()!!.create(ApiInterface::class.java)
                var requestCall: Call<CEOModel> = apiInterface.updateCEO(newCEO, CEOModel.id!!)

                requestCall.enqueue(object : Callback<CEOModel> {
                    override fun onFailure(call: Call<CEOModel>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "gagal tersimpan", Toast.LENGTH_LONG
                        ).show()


                    }

                    override fun onResponse(call: Call<CEOModel>, response: Response<CEOModel>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@MainActivity,
                                "berhasil tersimpan", Toast.LENGTH_LONG
                            ).show()
                            setupListOfDataIntoRecyclerView()
                            etName.setText("")
                            etCName.setText("")
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "gagal tersimpan", Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
                updateDialog.dismiss()
            }
        }
        updateDialog.tvcancel.setOnClickListener {
            updateDialog.dismiss()
        }
        updateDialog.show()
    }
}

