package logic.service

import com.twitter.logging.Logger
import com.twitter.util.Future
import common.Candidate

class LogicService(log: Logger) extends common.LogicService.FutureIface {
  override def calc(candidate: Candidate): Future[Long] = {
//    log.info(s"p1: ${candidate.p1}, p2: ${candidate.p2}")
    Future { candidate.p1.toLong + candidate.p2.getOrElse("0").toLong }
  }
}
