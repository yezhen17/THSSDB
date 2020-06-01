namespace java cn.edu.thssdb.rpc.thrift

struct Status {
  1: required i32 code;
  2: optional string msg;
}

struct GetTimeReq {
}

struct GetTimeResp {
  1: required string time
  2: required Status status
}

struct ConnectReq {
  1: required string username
  2: required string password
}

struct ConnectResp {
  1: required Status status
  2: required string information
  3: required i64 sessionId
}

struct DisconnectReq {
  1: required i64 sessionId
}

struct DisconnectResp {
  1: required Status status
  2: required string information
}

struct ExecuteStatementReq {
  1: required i64 sessionId
  2: required string statement
}

struct ExecuteStatementResp {
  1: required Status status
  2: required string information
  3: required bool isAbort
  4: required bool hasResult
  5: optional list<string> tableList              // only for query
  6: optional list<list<string>> columnsList      // only for query
  7: optional list<list<list<string>>> rowList    // only for query
}

service IService {
  GetTimeResp getTime(1: GetTimeReq req);
  ConnectResp connect(1: ConnectReq req);
  DisconnectResp disconnect(1: DisconnectReq req);
  ExecuteStatementResp executeStatement(1: ExecuteStatementReq req);
}
