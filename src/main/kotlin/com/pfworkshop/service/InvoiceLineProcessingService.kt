package com.pfworkshop.service

import com.pfworkshop.core.InvoiceLine
import org.apache.commons.lang3.StringUtils
import java.math.BigDecimal
import java.util.HashMap

class InvoiceLineProcessingService {

    fun processInvoiceLines(input : List<InvoiceLine>): List<InvoiceLine>  {
        val mapOfInvoices: HashMap<String, InvoiceLine> = HashMap<String, InvoiceLine>()
        val ihea: List<String>  = input
            .filter {
                it.description!!.contains("IHEA") || it.glAccountId!!.contains("IHEA") }
            .map {
                it.invoiceNumber!! }
            .distinct()

        val nonIHEA = input
            .filter {
                StringUtils.isNotBlank(it.paymentDate) && (BigDecimal(it.getAmount()?: "0") > BigDecimal.ZERO) }
            .filter {  !ihea.contains(it.invoiceNumber) }
            .sortedBy { it.paymentDate + it.invoiceNumber + it.glAccountId }

        nonIHEA.forEach {
            mapOfInvoices[it.invoiceNumber!!] = it;
        }

        return mapOfInvoices.values.sortedBy { it.paymentDate + it.invoiceNumber + it.glAccountId }
    }
}