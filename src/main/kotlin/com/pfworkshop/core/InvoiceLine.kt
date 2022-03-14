package com.pfworkshop.core

import com.opencsv.bean.CsvBindByPosition
import java.util.regex.Pattern

class InvoiceLine {

     @CsvBindByPosition(position = 0)
     val invoiceNumber: String? = null

     @CsvBindByPosition(position = 1)
     val invoiceDate: String? = null

     @CsvBindByPosition(position = 2)
     val billTo: String? = null

     @CsvBindByPosition(position = 3)
     val description: String? = null

     @CsvBindByPosition(position = 4)
     val type: String? = null

     @CsvBindByPosition(position = 5)
     val sourceSystem: String? = null

     @CsvBindByPosition(position = 6)
     val glAccountId: String? = null

     @CsvBindByPosition(position = 7)
     val accountingMethod: String? = null

     @CsvBindByPosition(position = 8)
     private val amount: String? = null

     @CsvBindByPosition(position = 9)
     val paymentDate: String? = null


    fun getAmount(): String? {
         return amount?.replace(",", "")
     }

     private fun jobCode(): String {
         val p = Pattern.compile(".+~(.+)")
         val m = p.matcher(glAccountId)
         return if (m.matches()) {
             m.group(1)
         } else ""
     }

     fun toOutput(): String? {
         return invoiceNumber + "," + invoiceDate + "," + billTo?.replace(",", " ") + "," +
                 description?.replace(",", " ") + "," + type + "," +
                 sourceSystem + "," + glAccountId + "," + jobCode() + "," + accountingMethod + "," +
                 getAmount() + "," + paymentDate
     }
 }