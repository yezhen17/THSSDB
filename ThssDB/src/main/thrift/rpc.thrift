namespace java cn.edu.thssdb.rpc.thrift

struct Status {
  1: required i32 code;
  2: optional string msg;
}

struct GetTimeReq {
}

struct ConnectReq{
  1: required string username
  2: required string password
}

struct ConnectResp{
  1: required Status status
  2: required i64 sessionId
  3: required string information
}

struct DisconnectReq{
  1: required i64 sessionId
}

struct DisconnectResp{
  1: required Status status
  2: required string information
}

struct GetTimeResp {
  1: required string time
  2: required Status status
}

struct ExecuteStatementReq {
  1: required i64 sessionId
  2: required string statement
}

struct ExecuteStatementResp{
  1: required Status status
  2: required bool isAbort
  3: required bool hasResult
  4: required string information
  // only for query
  5: optional list<string> columnsList
  6: optional list<list<string>> rowList
}

service IService {
  GetTimeResp getTime(1: GetTimeReq req);
  ConnectResp connect(1: ConnectReq req);
  DisconnectResp disconnect(1: DisconnectReq req);
  ExecuteStatementResp executeStatement(1: ExecuteStatementReq req);
}
