package bid.filter

import bid.model.BidRequest
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Filter, Service}
import com.twitter.logging.Logger
import com.twitter.util.Future

class RequestFilter(log: Logger) extends Filter[Request, Response, BidRequest, Response] {

  override def apply(request: Request, service: Service[BidRequest, Response]): Future[Response] = {
    val p1 = request.getParam("p1")
    val p2 = request.getParam("p2")
    val bidRequest = BidRequest(p1, Some(p2))
    service(bidRequest)
  }
}
