package controllers

import javax.activation.MimeType

import play.api.mvc._
import play.api.mvc.Results._
import play.mvc.{Controller}

/**
  * Created by smlee on 17. 10. 2.
  */
class Barcodes extends Controller {
  val ImageResolution = 144



  def barcode(ean: Long) = Action {
    import java.lang.IllegalArgumentException

    val mimeType = "image/png"
    try {
      val imageData = ean13BarCode(ean, mimeType)
      Ok(imageData).as(mimeType)
    } catch {
      case e: IllegalArgumentException =>
        BadRequest("Couldn't generate bar code. Error: " + e.getMessage)
    }
  }

  def ean13BarCode(ean: Long, mimeType: String) = {
    import java.io.ByteArrayOutputStream
    import java.awt.image.BufferedImage
    import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
    import org.krysalis.barcode4j.impl.upcean.EAN13Bean

    val output: ByteArrayOutputStream = new ByteArrayOutputStream
    val canvas: BitmapCanvasProvider = new BitmapCanvasProvider(output, mimeType, ImageResolution,
      BufferedImage.TYPE_BYTE_BINARY, false, 0)

    val barcode = new EAN13Bean()
    barcode.generateBarcode(canvas, String.valueOf(ean))
    canvas.finish

    output.toByteArray
  }
}
