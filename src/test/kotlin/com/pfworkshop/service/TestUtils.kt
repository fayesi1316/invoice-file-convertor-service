package com.pfworkshop.service

import com.pfworkshop.core.InvoiceLine
import java.lang.reflect.Field

class TestUtils {
    companion object {
         fun createInvoiceLine(invoiceNumber: String,
                                      description: String,
                                      glAccountId: String,
                                      paymentDate: String,
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

            privateField = InvoiceLine::class.java.getDeclaredField("paymentDate")
            privateField.isAccessible = true
            privateField.set(invoiceLine, paymentDate)

            privateField = InvoiceLine::class.java.getDeclaredField("amount")
            privateField.isAccessible = true
            privateField.set(invoiceLine, amount)

            return invoiceLine
        }
    }
}