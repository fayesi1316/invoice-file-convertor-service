package com.pfworkshop.service

import com.pfworkshop.service.TestUtils.Companion.createInvoiceLine
import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets

class OutputConvertedFileServiceTest {
    @Test
    fun `should output csv from list of invoice lines`() {
        val line1 = createInvoiceLine("I123", "an Invoice", "some Acct", "2012-02-23", "3,000")
        val line2 = createInvoiceLine("I124", "another Invoice", "a looking normal acct", "2012-02-24", "0")
        val service = OutputConvertedFileService()
        val streamingOutput = service.outputCSV(listOf(line1, line2))
        val baos = ByteArrayOutputStream()
        streamingOutput.write(baos)
        val result = String(baos.toByteArray(), StandardCharsets.UTF_8)
        assertEquals(
            "Invoice_Number,InvoiceDate,Bill_To,Description,Type,SOURCE_SYSTEM,GLAccountId,Job_Code,AccountingMethod,Amount,Last_Payment_Date\n" +
                "I123,null,null,an Invoice,null,null,some Acct,,null,3000,2012-02-23\n" +
                "I124,null,null,another Invoice,null,null,a looking normal acct,,null,0,2012-02-24\n", result)
    }
}