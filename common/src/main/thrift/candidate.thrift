namespace java common

struct Candidate {
1: i32 cpnId
2: string p1
3: optional string p2
}

service LogicService {
  i64 calc(1: Candidate candidate)
}
