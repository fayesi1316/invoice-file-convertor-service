package com.pfworkshop.service

import com.pfworkshop.service.TestUtils.Companion.createInvoiceLine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

import org.junit.Test

class InvoiceLineProcessingServiceTest {

    @Test
    fun `should remove from file if desc or glAccountId contains word IHEA`() {
        val line1 = createInvoiceLine("I123", "an IHEA Invoice", "some Acct", "2012-02-23", "3,000")
        val line2 = createInvoiceLine("I124", "an Invoice", "IHEA acct", "2012-02-24", "2,000")
        val line3 = createInvoiceLine("I125", "an Invoice", "normal acct", "2012-02-26", "2,400")
        val service = InvoiceLineProcessingService()
        val result = service.processInvoiceLines(listOf(line1, line2, line3))
        assertEquals(1, result.size)
        assertEquals( "I125", result[0].invoiceNumber)
    }

    @Test
    fun `should remove from file if invoiceNumber has been identified as IHEA invoice from previous line`() {
        val line1 = createInvoiceLine("I123", "an IHEA Invoice", "some Acct", "2012-02-23", "3,000")
        val line2 = createInvoiceLine("I123", "an Invoice", "a looking normal acct", "2012-02-24", "2,000")
        val service = InvoiceLineProcessingService()
        val result = service.processInvoiceLines(listOf(line1, line2))
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should remove from file if amount is zero`() {
        val line1 = createInvoiceLine("I123", "an Invoice", "some Acct", "2012-02-23", "3,000")
        val line2 = createInvoiceLine("I124", "another Invoice", "a looking normal acct", "2012-02-24", "0")
        val service = InvoiceLineProcessingService()
        val result = service.processInvoiceLines(listOf(line1, line2))
        assertEquals(1, result.size)
        assertEquals("I123", result[0].invoiceNumber)
    }

    @Test
    fun `should return unique invoice line if multiple nonIHEA invoices sharing the same invoiceNumber`() {
        val line1 = createInvoiceLine("I123", "1-1234 - an Invoice", "some Acct", "2012-02-23", "3,000")
        val line2 = createInvoiceLine("I123", "4-1234 - another Invoice", "a normal acct", "2012-02-23", "3,000")
        val line3 = createInvoiceLine("I125", "4-1235 - third Invoice", "a normal acct", "2012-02-23", "3,000")
        val service = InvoiceLineProcessingService()
        val result = service.processInvoiceLines(listOf(line1, line2, line3))
        assertEquals(2, result.size)
    }

    @Test
    fun `should return unique invoice lines in ascending order of payment date, invoiceNumber and glAccountId`() {
        val line1 = createInvoiceLine("I123", "first Invoice", "1-123~some Acct", "2012-02-23", "3,000")
        val line2 = createInvoiceLine("I123", "second Invoice", "4-234~a normal acct", "2012-02-23", "3,000")
        val line3 = createInvoiceLine("I124", "third Invoice", "4-321~a normal acct", "2012-02-22", "3,000")
        val line4 = createInvoiceLine("I125", "forth Invoice", "4-456~a normal acct", "2012-02-24", "3,000")
        val service = InvoiceLineProcessingService()
        val result = service.processInvoiceLines(listOf(line1, line2, line3, line4))
        assertEquals(3, result.size)
        assertEquals("third Invoice", result[0].description)
        assertEquals("second Invoice", result[1].description)
        assertEquals("forth Invoice", result[2].description)
    }
}