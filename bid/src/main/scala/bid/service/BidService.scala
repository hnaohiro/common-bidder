package bid.service

import bid.model.BidRequest
import com.twitter.finagle.{Service, http}
import com.twitter.finagle.http.Response
import com.twitter.logging.Logger
import com.twitter.util.Future
import common.{Candidate, LogicService}

class BidService(log: Logger, logicService: LogicService.ServiceIface) extends Service[BidRequest, Response] {

  override def apply(request: BidRequest): Future[Response] = {
    val candidate = Candidate(1, request.p1, request.p2)
    val args = LogicService.Calc.Args(candidate)

    val timeStart = System.currentTimeMillis()

    logicService.calc(args) map { price =>
      log.info(s"time: ${System.currentTimeMillis() - timeStart}")

      val res = http.Response(http.Status.Ok)
      res.setContentString(s"price: $price")
      res
    }
  }
}