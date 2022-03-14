package com.pfworkshop.core

import org.junit.Assert.*
import org.junit.Test
import java.lang.reflect.Field

class InvoiceLineTest {
    @Test
    fun `should remove thousand separator from amount field`() {
        val invoiceLine = createInvoiceLine("I1234", "Invoice for Si, Peifang",
            "4-4123~acct", "Bill to Si, Peifang", "34,123")

        assertEquals("34123", invoiceLine.getAmount())
    }

    @Test
    fun `should remove comma from description and billTo when printing to output`() {
        val invoiceLine = createInvoiceLine("I1234", "Invoice for Si, Peifang",
            "4-4123~acct", "Bill to Si, Peifang", "34,123")

        assertEquals("I1234,null,Bill to Si  Peifang,Invoice for Si  Peifang,null,null,4-4123~acct,acct,null,34123,null", invoiceLine.toOutput())
    }

    @Test
    fun `should extract jobId from glAccountId`() {
        val invoiceLine = createInvoiceLine("I1234", "Invoice for Si, Peifang",
            "4-4123~acct", "Bill to Si, Peifang", "34,123")

        assertTrue(invoiceLine.toOutput()!!.contains("4-4123~acct,acct,"))
    }

    private fun createInvoiceLine(invoiceNumber: String,
                                  description: String,
                                  glAccountId: String,
                                  billTo: String,
                                  amount: String): InvoiceLine {
        val invoiceLine = InvoiceLine()

        var privateField: Field = InvoiceLine::class.java.getDeclaredField("invoiceNumber")
        privateField.isAccessible = true
        privateField.set(invoiceLine, invoiceNumber)

        privateField = InvoiceLine::class.java.getDeclaredField("description")
        privateField.isAccessible = true
        privateField.set(invoiceLine, description)

        privateField = InvoiceLine::class.java.getDeclaredField("glAccountId")
        privateField.isAccessible = true
        privateField.set(invoiceLine, glAccountId)

        privateField = InvoiceLine::class.java.getDeclaredField("billTo")
        privateField.isAccessible = true
        privateField.set(invoiceLine, billTo)

        privateField = InvoiceLine::class.java.getDeclaredField("amount")
        privateField.isAccessible = true
        privateField.set(invoiceLine, amount)

        return invoiceLine
    }
}