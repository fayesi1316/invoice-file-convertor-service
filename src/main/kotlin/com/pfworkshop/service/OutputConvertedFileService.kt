package com.pfworkshop.service;

import com.pfworkshop.core.InvoiceLine
import java.io.*
import javax.ws.rs.core.StreamingOutput

class OutputConvertedFileService {

    @Throws(IOException::class)
    fun outputCSV(invoiceLines: List<InvoiceLine?>): StreamingOutput {
        val stream = StreamingOutput { out ->
            val writer: Writer = BufferedWriter(OutputStreamWriter(out))

            writer.appendLine("Invoice_Number,InvoiceDate,Bill_To,Description,Type,SOURCE_SYSTEM,GLAccountId,Job_Code,AccountingMethod,Amount,Last_Payment_Date")

            invoiceLines.map { it!!.toOutput() }
                .forEach { writer.appendLine(it) }

            writer.flush()
        }
        return stream
    }
}
