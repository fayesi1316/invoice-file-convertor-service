package com.pfworkshop.resources

import com.opencsv.bean.CsvToBeanBuilder
import com.pfworkshop.core.InvoiceLine
import com.pfworkshop.service.InvoiceLineProcessingService
import com.pfworkshop.service.OutputConvertedFileService
import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/invoice-file")
@Produces(MediaType.APPLICATION_JSON)
class FileResource @Inject constructor (
    private val invoiceLineProcessingService: InvoiceLineProcessingService,
    private val outputConvertedFileService: OutputConvertedFileService) {

    private val LOGGER: Logger = LoggerFactory.getLogger(FileResource::class.java)
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Throws(
        IOException::class
    )
    fun uploadFile(
        @FormDataParam("source") uploadedInputStream: InputStream?,
        @FormDataParam("source") fileDetail: FormDataContentDisposition
    ): Response? {

        val beans = CsvToBeanBuilder<InvoiceLine>(uploadedInputStream!!.reader())
            .withSkipLines(4)
            .withType(InvoiceLine::class.java)
            .build()
            .parse()

        val invoiceLines = invoiceLineProcessingService.processInvoiceLines(beans)
        return Response.ok(outputConvertedFileService.outputCSV(invoiceLines)).build()
    }
}