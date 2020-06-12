// Generated from D:/Tongtong/Course/Grade3_Spring/ThssDB/src/main/java/cn/edu/thssdb/parser\SQL.g4 by ANTLR 4.8
package cn.edu.thssdb.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, EQ=6, NE=7, LT=8, GT=9, LE=10, 
		GE=11, ADD=12, SUB=13, MUL=14, DIV=15, AND=16, OR=17, T_INT=18, T_LONG=19, 
		T_FLOAT=20, T_DOUBLE=21, T_STRING=22, K_ADD=23, K_ALL=24, K_ALTER=25, 
		K_AND=26, K_AS=27, K_ASC=28, K_AVG=29, K_BEGIN=30, K_BY=31, K_CHECKPOINT=32, 
		K_COLUMN=33, K_COMMIT=34, K_COUNT=35, K_CREATE=36, K_DATABASE=37, K_DATABASES=38, 
		K_DELETE=39, K_DESC=40, K_DISTINCT=41, K_DROP=42, K_EXISTS=43, K_FROM=44, 
		K_FULL=45, K_GRANT=46, K_IF=47, K_IS=48, K_IDENTIFIED=49, K_INNER=50, 
		K_INSERT=51, K_INTO=52, K_JOIN=53, K_KEY=54, K_LEFT=55, K_MAX=56, K_MIN=57, 
		K_NATURAL=58, K_NOT=59, K_NULL=60, K_ON=61, K_OR=62, K_ORDER=63, K_OUTER=64, 
		K_PRIMARY=65, K_QUIT=66, K_REVOKE=67, K_RIGHT=68, K_ROLLBACK=69, K_SAVEPOINT=70, 
		K_SELECT=71, K_SET=72, K_SHOW=73, K_SUM=74, K_TABLE=75, K_TO=76, K_TRANSACTION=77, 
		K_UPDATE=78, K_USE=79, K_USER=80, K_VALUES=81, K_VIEW=82, K_WHERE=83, 
		IDENTIFIER=84, INTEGER_LITERAL=85, FLOAT_LITERAL=86, NUMERIC_LITERAL=87, 
		EXPONENT=88, STRING_LITERAL=89, SINGLE_LINE_COMMENT=90, MULTILINE_COMMENT=91, 
		SPACES=92;
	public static final int
		RULE_parse = 0, RULE_sql_stmt_list = 1, RULE_sql_stmt = 2, RULE_checkpoint_stmt = 3, 
		RULE_savepoint_stmt = 4, RULE_rollback_stmt = 5, RULE_commit_stmt = 6, 
		RULE_begin_transaction_stmt = 7, RULE_alter_table_stmt = 8, RULE_create_db_stmt = 9, 
		RULE_drop_db_stmt = 10, RULE_create_user_stmt = 11, RULE_drop_user_stmt = 12, 
		RULE_create_table_stmt = 13, RULE_show_meta_stmt = 14, RULE_grant_stmt = 15, 
		RULE_revoke_stmt = 16, RULE_use_db_stmt = 17, RULE_delete_stmt = 18, RULE_drop_table_stmt = 19, 
		RULE_show_db_stmt = 20, RULE_quit_stmt = 21, RULE_show_table_stmt = 22, 
		RULE_insert_stmt = 23, RULE_value_entry = 24, RULE_select_stmt = 25, RULE_select_content = 26, 
		RULE_select_item = 27, RULE_select_item_2 = 28, RULE_join_content = 29, 
		RULE_on_content = 30, RULE_create_view_stmt = 31, RULE_drop_view_stmt = 32, 
		RULE_update_stmt = 33, RULE_column_def = 34, RULE_type_name = 35, RULE_multiple_condition = 36, 
		RULE_condition = 37, RULE_comparer = 38, RULE_comparator = 39, RULE_table_constraint = 40, 
		RULE_result_column = 41, RULE_auth_level = 42, RULE_literal_value = 43, 
		RULE_column_full_name = 44, RULE_database_name = 45, RULE_table_name = 46, 
		RULE_user_name = 47, RULE_column_name = 48, RULE_view_name = 49, RULE_savepoint_name = 50, 
		RULE_password = 51, RULE_numeric_value = 52;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "sql_stmt_list", "sql_stmt", "checkpoint_stmt", "savepoint_stmt", 
			"rollback_stmt", "commit_stmt", "begin_transaction_stmt", "alter_table_stmt", 
			"create_db_stmt", "drop_db_stmt", "create_user_stmt", "drop_user_stmt", 
			"create_table_stmt", "show_meta_stmt", "grant_stmt", "revoke_stmt", "use_db_stmt", 
			"delete_stmt", "drop_table_stmt", "show_db_stmt", "quit_stmt", "show_table_stmt", 
			"insert_stmt", "value_entry", "select_stmt", "select_content", "select_item", 
			"select_item_2", "join_content", "on_content", "create_view_stmt", "drop_view_stmt", 
			"update_stmt", "column_def", "type_name", "multiple_condition", "condition", 
			"comparer", "comparator", "table_constraint", "result_column", "auth_level", 
			"literal_value", "column_full_name", "database_name", "table_name", "user_name", 
			"column_name", "view_name", "savepoint_name", "password", "numeric_value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'('", "','", "')'", "'.'", "'='", "'<>'", "'<'", "'>'", 
			"'<='", "'>='", "'+'", "'-'", "'*'", "'/'", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "EQ", "NE", "LT", "GT", "LE", "GE", 
			"ADD", "SUB", "MUL", "DIV", "AND", "OR", "T_INT", "T_LONG", "T_FLOAT", 
			"T_DOUBLE", "T_STRING", "K_ADD", "K_ALL", "K_ALTER", "K_AND", "K_AS", 
			"K_ASC", "K_AVG", "K_BEGIN", "K_BY", "K_CHECKPOINT", "K_COLUMN", "K_COMMIT", 
			"K_COUNT", "K_CREATE", "K_DATABASE", "K_DATABASES", "K_DELETE", "K_DESC", 
			"K_DISTINCT", "K_DROP", "K_EXISTS", "K_FROM", "K_FULL", "K_GRANT", "K_IF", 
			"K_IS", "K_IDENTIFIED", "K_INNER", "K_INSERT", "K_INTO", "K_JOIN", "K_KEY", 
			"K_LEFT", "K_MAX", "K_MIN", "K_NATURAL", "K_NOT", "K_NULL", "K_ON", "K_OR", 
			"K_ORDER", "K_OUTER", "K_PRIMARY", "K_QUIT", "K_REVOKE", "K_RIGHT", "K_ROLLBACK", 
			"K_SAVEPOINT", "K_SELECT", "K_SET", "K_SHOW", "K_SUM", "K_TABLE", "K_TO", 
			"K_TRANSACTION", "K_UPDATE", "K_USE", "K_USER", "K_VALUES", "K_VIEW", 
			"K_WHERE", "IDENTIFIER", "INTEGER_LITERAL", "FLOAT_LITERAL", "NUMERIC_LITERAL", 
			"EXPONENT", "STRING_LITERAL", "SINGLE_LINE_COMMENT", "MULTILINE_COMMENT", 
			"SPACES"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ParseContext extends ParserRuleContext {
		public Sql_stmt_listContext sql_stmt_list() {
			return getRuleContext(Sql_stmt_listContext.class,0);
		}
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitParse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			sql_stmt_list();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Sql_stmt_listContext extends ParserRuleContext {
		public List<Sql_stmtContext> sql_stmt() {
			return getRuleContexts(Sql_stmtContext.class);
		}
		public Sql_stmtContext sql_stmt(int i) {
			return getRuleContext(Sql_stmtContext.class,i);
		}
		public Sql_stmt_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sql_stmt_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSql_stmt_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSql_stmt_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSql_stmt_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sql_stmt_listContext sql_stmt_list() throws RecognitionException {
		Sql_stmt_listContext _localctx = new Sql_stmt_listContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sql_stmt_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(108);
				match(T__0);
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(114);
			sql_stmt();
			setState(123);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(116); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(115);
						match(T__0);
						}
						}
						setState(118); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__0 );
					setState(120);
					sql_stmt();
					}
					} 
				}
				setState(125);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(129);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(126);
				match(T__0);
				}
				}
				setState(131);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Sql_stmtContext extends ParserRuleContext {
		public Alter_table_stmtContext alter_table_stmt() {
			return getRuleContext(Alter_table_stmtContext.class,0);
		}
		public Create_table_stmtContext create_table_stmt() {
			return getRuleContext(Create_table_stmtContext.class,0);
		}
		public Create_db_stmtContext create_db_stmt() {
			return getRuleContext(Create_db_stmtContext.class,0);
		}
		public Create_user_stmtContext create_user_stmt() {
			return getRuleContext(Create_user_stmtContext.class,0);
		}
		public Drop_db_stmtContext drop_db_stmt() {
			return getRuleContext(Drop_db_stmtContext.class,0);
		}
		public Drop_user_stmtContext drop_user_stmt() {
			return getRuleContext(Drop_user_stmtContext.class,0);
		}
		public Delete_stmtContext delete_stmt() {
			return getRuleContext(Delete_stmtContext.class,0);
		}
		public Drop_table_stmtContext drop_table_stmt() {
			return getRuleContext(Drop_table_stmtContext.class,0);
		}
		public Insert_stmtContext insert_stmt() {
			return getRuleContext(Insert_stmtContext.class,0);
		}
		public Select_stmtContext select_stmt() {
			return getRuleContext(Select_stmtContext.class,0);
		}
		public Create_view_stmtContext create_view_stmt() {
			return getRuleContext(Create_view_stmtContext.class,0);
		}
		public Drop_view_stmtContext drop_view_stmt() {
			return getRuleContext(Drop_view_stmtContext.class,0);
		}
		public Grant_stmtContext grant_stmt() {
			return getRuleContext(Grant_stmtContext.class,0);
		}
		public Revoke_stmtContext revoke_stmt() {
			return getRuleContext(Revoke_stmtContext.class,0);
		}
		public Use_db_stmtContext use_db_stmt() {
			return getRuleContext(Use_db_stmtContext.class,0);
		}
		public Show_db_stmtContext show_db_stmt() {
			return getRuleContext(Show_db_stmtContext.class,0);
		}
		public Show_table_stmtContext show_table_stmt() {
			return getRuleContext(Show_table_stmtContext.class,0);
		}
		public Show_meta_stmtContext show_meta_stmt() {
			return getRuleContext(Show_meta_stmtContext.class,0);
		}
		public Quit_stmtContext quit_stmt() {
			return getRuleContext(Quit_stmtContext.class,0);
		}
		public Update_stmtContext update_stmt() {
			return getRuleContext(Update_stmtContext.class,0);
		}
		public Begin_transaction_stmtContext begin_transaction_stmt() {
			return getRuleContext(Begin_transaction_stmtContext.class,0);
		}
		public Commit_stmtContext commit_stmt() {
			return getRuleContext(Commit_stmtContext.class,0);
		}
		public Rollback_stmtContext rollback_stmt() {
			return getRuleContext(Rollback_stmtContext.class,0);
		}
		public Savepoint_stmtContext savepoint_stmt() {
			return getRuleContext(Savepoint_stmtContext.class,0);
		}
		public Checkpoint_stmtContext checkpoint_stmt() {
			return getRuleContext(Checkpoint_stmtContext.class,0);
		}
		public Sql_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sql_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSql_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSql_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSql_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sql_stmtContext sql_stmt() throws RecognitionException {
		Sql_stmtContext _localctx = new Sql_stmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_sql_stmt);
		try {
			setState(157);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				alter_table_stmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				create_table_stmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(134);
				create_db_stmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(135);
				create_user_stmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(136);
				drop_db_stmt();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(137);
				drop_user_stmt();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(138);
				delete_stmt();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(139);
				drop_table_stmt();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(140);
				insert_stmt();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(141);
				select_stmt();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(142);
				create_view_stmt();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(143);
				drop_view_stmt();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(144);
				grant_stmt();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(145);
				revoke_stmt();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(146);
				use_db_stmt();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(147);
				show_db_stmt();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(148);
				show_table_stmt();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(149);
				show_meta_stmt();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(150);
				quit_stmt();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(151);
				update_stmt();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(152);
				begin_transaction_stmt();
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(153);
				commit_stmt();
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 23);
				{
				setState(154);
				rollback_stmt();
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 24);
				{
				setState(155);
				savepoint_stmt();
				}
				break;
			case 25:
				enterOuterAlt(_localctx, 25);
				{
				setState(156);
				checkpoint_stmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Checkpoint_stmtContext extends ParserRuleContext {
		public TerminalNode K_CHECKPOINT() { return getToken(SQLParser.K_CHECKPOINT, 0); }
		public Checkpoint_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_checkpoint_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCheckpoint_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCheckpoint_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCheckpoint_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Checkpoint_stmtContext checkpoint_stmt() throws RecognitionException {
		Checkpoint_stmtContext _localctx = new Checkpoint_stmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_checkpoint_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			match(K_CHECKPOINT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Savepoint_stmtContext extends ParserRuleContext {
		public TerminalNode K_SAVEPOINT() { return getToken(SQLParser.K_SAVEPOINT, 0); }
		public Savepoint_nameContext savepoint_name() {
			return getRuleContext(Savepoint_nameContext.class,0);
		}
		public Savepoint_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_savepoint_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSavepoint_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSavepoint_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSavepoint_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Savepoint_stmtContext savepoint_stmt() throws RecognitionException {
		Savepoint_stmtContext _localctx = new Savepoint_stmtContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_savepoint_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(K_SAVEPOINT);
			setState(162);
			savepoint_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rollback_stmtContext extends ParserRuleContext {
		public TerminalNode K_ROLLBACK() { return getToken(SQLParser.K_ROLLBACK, 0); }
		public Savepoint_nameContext savepoint_name() {
			return getRuleContext(Savepoint_nameContext.class,0);
		}
		public Rollback_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rollback_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterRollback_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitRollback_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitRollback_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rollback_stmtContext rollback_stmt() throws RecognitionException {
		Rollback_stmtContext _localctx = new Rollback_stmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_rollback_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(K_ROLLBACK);
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(165);
				savepoint_name();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Commit_stmtContext extends ParserRuleContext {
		public TerminalNode K_COMMIT() { return getToken(SQLParser.K_COMMIT, 0); }
		public Commit_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commit_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCommit_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCommit_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCommit_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Commit_stmtContext commit_stmt() throws RecognitionException {
		Commit_stmtContext _localctx = new Commit_stmtContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_commit_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(K_COMMIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Begin_transaction_stmtContext extends ParserRuleContext {
		public TerminalNode K_BEGIN() { return getToken(SQLParser.K_BEGIN, 0); }
		public TerminalNode K_TRANSACTION() { return getToken(SQLParser.K_TRANSACTION, 0); }
		public Begin_transaction_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_begin_transaction_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterBegin_transaction_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitBegin_transaction_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitBegin_transaction_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Begin_transaction_stmtContext begin_transaction_stmt() throws RecognitionException {
		Begin_transaction_stmtContext _localctx = new Begin_transaction_stmtContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_begin_transaction_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(K_BEGIN);
			setState(171);
			match(K_TRANSACTION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Alter_table_stmtContext extends ParserRuleContext {
		public List<TerminalNode> K_ALTER() { return getTokens(SQLParser.K_ALTER); }
		public TerminalNode K_ALTER(int i) {
			return getToken(SQLParser.K_ALTER, i);
		}
		public TerminalNode K_TABLE() { return getToken(SQLParser.K_TABLE, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public TerminalNode K_ADD() { return getToken(SQLParser.K_ADD, 0); }
		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class,0);
		}
		public Type_nameContext type_name() {
			return getRuleContext(Type_nameContext.class,0);
		}
		public TerminalNode K_DROP() { return getToken(SQLParser.K_DROP, 0); }
		public TerminalNode K_COLUMN() { return getToken(SQLParser.K_COLUMN, 0); }
		public Alter_table_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alter_table_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterAlter_table_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitAlter_table_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitAlter_table_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Alter_table_stmtContext alter_table_stmt() throws RecognitionException {
		Alter_table_stmtContext _localctx = new Alter_table_stmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_alter_table_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(K_ALTER);
			setState(174);
			match(K_TABLE);
			setState(175);
			table_name();
			setState(188);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case K_ADD:
				{
				setState(176);
				match(K_ADD);
				setState(177);
				column_name();
				setState(178);
				type_name();
				}
				break;
			case K_DROP:
				{
				setState(180);
				match(K_DROP);
				setState(181);
				match(K_COLUMN);
				setState(182);
				column_name();
				}
				break;
			case K_ALTER:
				{
				setState(183);
				match(K_ALTER);
				setState(184);
				match(K_COLUMN);
				setState(185);
				column_name();
				setState(186);
				type_name();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Create_db_stmtContext extends ParserRuleContext {
		public TerminalNode K_CREATE() { return getToken(SQLParser.K_CREATE, 0); }
		public TerminalNode K_DATABASE() { return getToken(SQLParser.K_DATABASE, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
		}
		public Create_db_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_create_db_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCreate_db_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCreate_db_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCreate_db_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Create_db_stmtContext create_db_stmt() throws RecognitionException {
		Create_db_stmtContext _localctx = new Create_db_stmtContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_create_db_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(K_CREATE);
			setState(191);
			match(K_DATABASE);
			setState(192);
			database_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Drop_db_stmtContext extends ParserRuleContext {
		public TerminalNode K_DROP() { return getToken(SQLParser.K_DROP, 0); }
		public TerminalNode K_DATABASE() { return getToken(SQLParser.K_DATABASE, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
		}
		public TerminalNode K_IF() { return getToken(SQLParser.K_IF, 0); }
		public TerminalNode K_EXISTS() { return getToken(SQLParser.K_EXISTS, 0); }
		public Drop_db_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_drop_db_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDrop_db_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDrop_db_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDrop_db_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Drop_db_stmtContext drop_db_stmt() throws RecognitionException {
		Drop_db_stmtContext _localctx = new Drop_db_stmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_drop_db_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(K_DROP);
			setState(195);
			match(K_DATABASE);
			setState(198);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==K_IF) {
				{
				setState(196);
				match(K_IF);
				setState(197);
				match(K_EXISTS);
				}
			}

			setState(200);
			database_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Create_user_stmtContext extends ParserRuleContext {
		public TerminalNode K_CREATE() { return getToken(SQLParser.K_CREATE, 0); }
		public TerminalNode K_USER() { return getToken(SQLParser.K_USER, 0); }
		public User_nameContext user_name() {
			return getRuleContext(User_nameContext.class,0);
		}
		public TerminalNode K_IDENTIFIED() { return getToken(SQLParser.K_IDENTIFIED, 0); }
		public TerminalNode K_BY() { return getToken(SQLParser.K_BY, 0); }
		public PasswordContext password() {
			return getRuleContext(PasswordContext.class,0);
		}
		public Create_user_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_create_user_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCreate_user_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCreate_user_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCreate_user_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Create_user_stmtContext create_user_stmt() throws RecognitionException {
		Create_user_stmtContext _localctx = new Create_user_stmtContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_create_user_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(K_CREATE);
			setState(203);
			match(K_USER);
			setState(204);
			user_name();
			setState(205);
			match(K_IDENTIFIED);
			setState(206);
			match(K_BY);
			setState(207);
			password();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Drop_user_stmtContext extends ParserRuleContext {
		public TerminalNode K_DROP() { return getToken(SQLParser.K_DROP, 0); }
		public TerminalNode K_USER() { return getToken(SQLParser.K_USER, 0); }
		public User_nameContext user_name() {
			return getRuleContext(User_nameContext.class,0);
		}
		public TerminalNode K_IF() { return getToken(SQLParser.K_IF, 0); }
		public TerminalNode K_EXISTS() { return getToken(SQLParser.K_EXISTS, 0); }
		public Drop_user_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_drop_user_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDrop_user_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDrop_user_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDrop_user_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Drop_user_stmtContext drop_user_stmt() throws RecognitionException {
		Drop_user_stmtContext _localctx = new Drop_user_stmtContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_drop_user_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(K_DROP);
			setState(210);
			match(K_USER);
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==K_IF) {
				{
				setState(211);
				match(K_IF);
				setState(212);
				match(K_EXISTS);
				}
			}

			setState(215);
			user_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Create_table_stmtContext extends ParserRuleContext {
		public TerminalNode K_CREATE() { return getToken(SQLParser.K_CREATE, 0); }
		public TerminalNode K_TABLE() { return getToken(SQLParser.K_TABLE, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public List<Column_defContext> column_def() {
			return getRuleContexts(Column_defContext.class);
		}
		public Column_defContext column_def(int i) {
			return getRuleContext(Column_defContext.class,i);
		}
		public Table_constraintContext table_constraint() {
			return getRuleContext(Table_constraintContext.class,0);
		}
		public Create_table_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_create_table_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCreate_table_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCreate_table_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCreate_table_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Create_table_stmtContext create_table_stmt() throws RecognitionException {
		Create_table_stmtContext _localctx = new Create_table_stmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_create_table_stmt);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			match(K_CREATE);
			setState(218);
			match(K_TABLE);
			setState(219);
			table_name();
			setState(220);
			match(T__1);
			setState(221);
			column_def();
			setState(226);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(222);
					match(T__2);
					setState(223);
					column_def();
					}
					} 
				}
				setState(228);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			setState(231);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(229);
				match(T__2);
				setState(230);
				table_constraint();
				}
			}

			setState(233);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Show_meta_stmtContext extends ParserRuleContext {
		public TerminalNode K_SHOW() { return getToken(SQLParser.K_SHOW, 0); }
		public TerminalNode K_TABLE() { return getToken(SQLParser.K_TABLE, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public Show_meta_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_show_meta_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterShow_meta_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitShow_meta_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitShow_meta_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Show_meta_stmtContext show_meta_stmt() throws RecognitionException {
		Show_meta_stmtContext _localctx = new Show_meta_stmtContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_show_meta_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(K_SHOW);
			setState(236);
			match(K_TABLE);
			setState(237);
			table_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Grant_stmtContext extends ParserRuleContext {
		public TerminalNode K_GRANT() { return getToken(SQLParser.K_GRANT, 0); }
		public List<Auth_levelContext> auth_level() {
			return getRuleContexts(Auth_levelContext.class);
		}
		public Auth_levelContext auth_level(int i) {
			return getRuleContext(Auth_levelContext.class,i);
		}
		public TerminalNode K_ON() { return getToken(SQLParser.K_ON, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public TerminalNode K_TO() { return getToken(SQLParser.K_TO, 0); }
		public User_nameContext user_name() {
			return getRuleContext(User_nameContext.class,0);
		}
		public Grant_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grant_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterGrant_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitGrant_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitGrant_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Grant_stmtContext grant_stmt() throws RecognitionException {
		Grant_stmtContext _localctx = new Grant_stmtContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_grant_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(K_GRANT);
			setState(240);
			auth_level();
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(241);
				match(T__2);
				setState(242);
				auth_level();
				}
				}
				setState(247);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(248);
			match(K_ON);
			setState(249);
			table_name();
			setState(250);
			match(K_TO);
			setState(251);
			user_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Revoke_stmtContext extends ParserRuleContext {
		public TerminalNode K_REVOKE() { return getToken(SQLParser.K_REVOKE, 0); }
		public List<Auth_levelContext> auth_level() {
			return getRuleContexts(Auth_levelContext.class);
		}
		public Auth_levelContext auth_level(int i) {
			return getRuleContext(Auth_levelContext.class,i);
		}
		public TerminalNode K_ON() { return getToken(SQLParser.K_ON, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public TerminalNode K_FROM() { return getToken(SQLParser.K_FROM, 0); }
		public User_nameContext user_name() {
			return getRuleContext(User_nameContext.class,0);
		}
		public Revoke_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_revoke_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterRevoke_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitRevoke_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitRevoke_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Revoke_stmtContext revoke_stmt() throws RecognitionException {
		Revoke_stmtContext _localctx = new Revoke_stmtContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_revoke_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(K_REVOKE);
			setState(254);
			auth_level();
			setState(259);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(255);
				match(T__2);
				setState(256);
				auth_level();
				}
				}
				setState(261);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(262);
			match(K_ON);
			setState(263);
			table_name();
			setState(264);
			match(K_FROM);
			setState(265);
			user_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Use_db_stmtContext extends ParserRuleContext {
		public TerminalNode K_USE() { return getToken(SQLParser.K_USE, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
		}
		public Use_db_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_use_db_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterUse_db_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitUse_db_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUse_db_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Use_db_stmtContext use_db_stmt() throws RecognitionException {
		Use_db_stmtContext _localctx = new Use_db_stmtContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_use_db_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(K_USE);
			setState(268);
			database_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Delete_stmtContext extends ParserRuleContext {
		public TerminalNode K_DELETE() { return getToken(SQLParser.K_DELETE, 0); }
		public TerminalNode K_FROM() { return getToken(SQLParser.K_FROM, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public TerminalNode K_WHERE() { return getToken(SQLParser.K_WHERE, 0); }
		public Multiple_conditionContext multiple_condition() {
			return getRuleContext(Multiple_conditionContext.class,0);
		}
		public Delete_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delete_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDelete_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDelete_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDelete_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Delete_stmtContext delete_stmt() throws RecognitionException {
		Delete_stmtContext _localctx = new Delete_stmtContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_delete_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(K_DELETE);
			setState(271);
			match(K_FROM);
			setState(272);
			table_name();
			setState(275);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==K_WHERE) {
				{
				setState(273);
				match(K_WHERE);
				setState(274);
				multiple_condition(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Drop_table_stmtContext extends ParserRuleContext {
		public TerminalNode K_DROP() { return getToken(SQLParser.K_DROP, 0); }
		public TerminalNode K_TABLE() { return getToken(SQLParser.K_TABLE, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public TerminalNode K_IF() { return getToken(SQLParser.K_IF, 0); }
		public TerminalNode K_EXISTS() { return getToken(SQLParser.K_EXISTS, 0); }
		public Drop_table_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_drop_table_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDrop_table_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDrop_table_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDrop_table_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Drop_table_stmtContext drop_table_stmt() throws RecognitionException {
		Drop_table_stmtContext _localctx = new Drop_table_stmtContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_drop_table_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(K_DROP);
			setState(278);
			match(K_TABLE);
			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==K_IF) {
				{
				setState(279);
				match(K_IF);
				setState(280);
				match(K_EXISTS);
				}
			}

			setState(283);
			table_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Show_db_stmtContext extends ParserRuleContext {
		public TerminalNode K_SHOW() { return getToken(SQLParser.K_SHOW, 0); }
		public TerminalNode K_DATABASES() { return getToken(SQLParser.K_DATABASES, 0); }
		public Show_db_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_show_db_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterShow_db_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitShow_db_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitShow_db_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Show_db_stmtContext show_db_stmt() throws RecognitionException {
		Show_db_stmtContext _localctx = new Show_db_stmtContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_show_db_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(K_SHOW);
			setState(286);
			match(K_DATABASES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Quit_stmtContext extends ParserRuleContext {
		public TerminalNode K_QUIT() { return getToken(SQLParser.K_QUIT, 0); }
		public Quit_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quit_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterQuit_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitQuit_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitQuit_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Quit_stmtContext quit_stmt() throws RecognitionException {
		Quit_stmtContext _localctx = new Quit_stmtContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_quit_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(K_QUIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Show_table_stmtContext extends ParserRuleContext {
		public TerminalNode K_SHOW() { return getToken(SQLParser.K_SHOW, 0); }
		public TerminalNode K_TABLE() { return getToken(SQLParser.K_TABLE, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public Show_table_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_show_table_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterShow_table_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitShow_table_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitShow_table_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Show_table_stmtContext show_table_stmt() throws RecognitionException {
		Show_table_stmtContext _localctx = new Show_table_stmtContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_show_table_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(K_SHOW);
			setState(291);
			match(K_TABLE);
			setState(292);
			table_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Insert_stmtContext extends ParserRuleContext {
		public TerminalNode K_INSERT() { return getToken(SQLParser.K_INSERT, 0); }
		public TerminalNode K_INTO() { return getToken(SQLParser.K_INTO, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public TerminalNode K_VALUES() { return getToken(SQLParser.K_VALUES, 0); }
		public List<Value_entryContext> value_entry() {
			return getRuleContexts(Value_entryContext.class);
		}
		public Value_entryContext value_entry(int i) {
			return getRuleContext(Value_entryContext.class,i);
		}
		public List<Column_nameContext> column_name() {
			return getRuleContexts(Column_nameContext.class);
		}
		public Column_nameContext column_name(int i) {
			return getRuleContext(Column_nameContext.class,i);
		}
		public Insert_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insert_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterInsert_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitInsert_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitInsert_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Insert_stmtContext insert_stmt() throws RecognitionException {
		Insert_stmtContext _localctx = new Insert_stmtContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_insert_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			match(K_INSERT);
			setState(295);
			match(K_INTO);
			setState(296);
			table_name();
			setState(308);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(297);
				match(T__1);
				setState(298);
				column_name();
				setState(303);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(299);
					match(T__2);
					setState(300);
					column_name();
					}
					}
					setState(305);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(306);
				match(T__3);
				}
			}

			setState(310);
			match(K_VALUES);
			setState(311);
			value_entry();
			setState(316);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(312);
				match(T__2);
				setState(313);
				value_entry();
				}
				}
				setState(318);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Value_entryContext extends ParserRuleContext {
		public List<Literal_valueContext> literal_value() {
			return getRuleContexts(Literal_valueContext.class);
		}
		public Literal_valueContext literal_value(int i) {
			return getRuleContext(Literal_valueContext.class,i);
		}
		public Value_entryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value_entry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterValue_entry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitValue_entry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitValue_entry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Value_entryContext value_entry() throws RecognitionException {
		Value_entryContext _localctx = new Value_entryContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_value_entry);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			match(T__1);
			setState(320);
			literal_value();
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(321);
				match(T__2);
				setState(322);
				literal_value();
				}
				}
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(328);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_stmtContext extends ParserRuleContext {
		public TerminalNode K_SELECT() { return getToken(SQLParser.K_SELECT, 0); }
		public Select_contentContext select_content() {
			return getRuleContext(Select_contentContext.class,0);
		}
		public TerminalNode K_FROM() { return getToken(SQLParser.K_FROM, 0); }
		public Join_contentContext join_content() {
			return getRuleContext(Join_contentContext.class,0);
		}
		public TerminalNode K_WHERE() { return getToken(SQLParser.K_WHERE, 0); }
		public Multiple_conditionContext multiple_condition() {
			return getRuleContext(Multiple_conditionContext.class,0);
		}
		public TerminalNode K_ORDER() { return getToken(SQLParser.K_ORDER, 0); }
		public TerminalNode K_BY() { return getToken(SQLParser.K_BY, 0); }
		public List<Column_full_nameContext> column_full_name() {
			return getRuleContexts(Column_full_nameContext.class);
		}
		public Column_full_nameContext column_full_name(int i) {
			return getRuleContext(Column_full_nameContext.class,i);
		}
		public TerminalNode K_DESC() { return getToken(SQLParser.K_DESC, 0); }
		public TerminalNode K_ASC() { return getToken(SQLParser.K_ASC, 0); }
		public Select_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSelect_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSelect_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSelect_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Select_stmtContext select_stmt() throws RecognitionException {
		Select_stmtContext _localctx = new Select_stmtContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_select_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			match(K_SELECT);
			setState(331);
			select_content();
			setState(332);
			match(K_FROM);
			setState(333);
			join_content();
			setState(336);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==K_WHERE) {
				{
				setState(334);
				match(K_WHERE);
				setState(335);
				multiple_condition(0);
				}
			}

			setState(351);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==K_ORDER) {
				{
				setState(338);
				match(K_ORDER);
				setState(339);
				match(K_BY);
				setState(340);
				column_full_name();
				setState(345);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(341);
					match(T__2);
					setState(342);
					column_full_name();
					}
					}
					setState(347);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(349);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_ASC || _la==K_DESC) {
					{
					setState(348);
					_la = _input.LA(1);
					if ( !(_la==K_ASC || _la==K_DESC) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_contentContext extends ParserRuleContext {
		public List<Select_itemContext> select_item() {
			return getRuleContexts(Select_itemContext.class);
		}
		public Select_itemContext select_item(int i) {
			return getRuleContext(Select_itemContext.class,i);
		}
		public TerminalNode K_DISTINCT() { return getToken(SQLParser.K_DISTINCT, 0); }
		public TerminalNode K_ALL() { return getToken(SQLParser.K_ALL, 0); }
		public List<Select_item_2Context> select_item_2() {
			return getRuleContexts(Select_item_2Context.class);
		}
		public Select_item_2Context select_item_2(int i) {
			return getRuleContext(Select_item_2Context.class,i);
		}
		public Select_contentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSelect_content(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSelect_content(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSelect_content(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Select_contentContext select_content() throws RecognitionException {
		Select_contentContext _localctx = new Select_contentContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_select_content);
		int _la;
		try {
			setState(375);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(354);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_ALL || _la==K_DISTINCT) {
					{
					setState(353);
					_la = _input.LA(1);
					if ( !(_la==K_ALL || _la==K_DISTINCT) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				{
				setState(356);
				select_item();
				setState(361);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(357);
					match(T__2);
					setState(358);
					select_item();
					}
					}
					setState(363);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(365);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_ALL || _la==K_DISTINCT) {
					{
					setState(364);
					_la = _input.LA(1);
					if ( !(_la==K_ALL || _la==K_DISTINCT) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				{
				setState(367);
				select_item_2();
				setState(372);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(368);
					match(T__2);
					setState(369);
					select_item_2();
					}
					}
					setState(374);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_itemContext extends ParserRuleContext {
		public List<Numeric_valueContext> numeric_value() {
			return getRuleContexts(Numeric_valueContext.class);
		}
		public Numeric_valueContext numeric_value(int i) {
			return getRuleContext(Numeric_valueContext.class,i);
		}
		public Result_columnContext result_column() {
			return getRuleContext(Result_columnContext.class,0);
		}
		public Column_full_nameContext column_full_name() {
			return getRuleContext(Column_full_nameContext.class,0);
		}
		public TerminalNode MUL() { return getToken(SQLParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(SQLParser.DIV, 0); }
		public TerminalNode ADD() { return getToken(SQLParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(SQLParser.SUB, 0); }
		public Select_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSelect_item(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSelect_item(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSelect_item(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Select_itemContext select_item() throws RecognitionException {
		Select_itemContext _localctx = new Select_itemContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_select_item);
		int _la;
		try {
			setState(391);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(377);
				numeric_value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(378);
				result_column();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(379);
				column_full_name();
				setState(380);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(381);
				numeric_value();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(383);
				numeric_value();
				setState(384);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(385);
				numeric_value();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(387);
				numeric_value();
				setState(388);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(389);
				column_full_name();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_item_2Context extends ParserRuleContext {
		public Column_full_nameContext column_full_name() {
			return getRuleContext(Column_full_nameContext.class,0);
		}
		public TerminalNode K_AVG() { return getToken(SQLParser.K_AVG, 0); }
		public TerminalNode K_MAX() { return getToken(SQLParser.K_MAX, 0); }
		public TerminalNode K_MIN() { return getToken(SQLParser.K_MIN, 0); }
		public TerminalNode K_COUNT() { return getToken(SQLParser.K_COUNT, 0); }
		public TerminalNode K_SUM() { return getToken(SQLParser.K_SUM, 0); }
		public TerminalNode MUL() { return getToken(SQLParser.MUL, 0); }
		public Select_item_2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_item_2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSelect_item_2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSelect_item_2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSelect_item_2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Select_item_2Context select_item_2() throws RecognitionException {
		Select_item_2Context _localctx = new Select_item_2Context(_ctx, getState());
		enterRule(_localctx, 56, RULE_select_item_2);
		int _la;
		try {
			setState(402);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(393);
				_la = _input.LA(1);
				if ( !(((((_la - 29)) & ~0x3f) == 0 && ((1L << (_la - 29)) & ((1L << (K_AVG - 29)) | (1L << (K_COUNT - 29)) | (1L << (K_MAX - 29)) | (1L << (K_MIN - 29)) | (1L << (K_SUM - 29)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(394);
				match(T__1);
				setState(395);
				column_full_name();
				setState(396);
				match(T__3);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(398);
				match(K_COUNT);
				setState(399);
				match(T__1);
				setState(400);
				match(MUL);
				setState(401);
				match(T__3);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Join_contentContext extends ParserRuleContext {
		public List<Table_nameContext> table_name() {
			return getRuleContexts(Table_nameContext.class);
		}
		public Table_nameContext table_name(int i) {
			return getRuleContext(Table_nameContext.class,i);
		}
		public TerminalNode K_NATURAL() { return getToken(SQLParser.K_NATURAL, 0); }
		public TerminalNode K_JOIN() { return getToken(SQLParser.K_JOIN, 0); }
		public TerminalNode K_INNER() { return getToken(SQLParser.K_INNER, 0); }
		public TerminalNode K_ON() { return getToken(SQLParser.K_ON, 0); }
		public On_contentContext on_content() {
			return getRuleContext(On_contentContext.class,0);
		}
		public TerminalNode K_LEFT() { return getToken(SQLParser.K_LEFT, 0); }
		public TerminalNode K_RIGHT() { return getToken(SQLParser.K_RIGHT, 0); }
		public TerminalNode K_FULL() { return getToken(SQLParser.K_FULL, 0); }
		public TerminalNode K_OUTER() { return getToken(SQLParser.K_OUTER, 0); }
		public Join_contentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterJoin_content(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitJoin_content(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitJoin_content(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Join_contentContext join_content() throws RecognitionException {
		Join_contentContext _localctx = new Join_contentContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_join_content);
		int _la;
		try {
			setState(436);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(404);
				table_name();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(405);
				table_name();
				setState(406);
				match(T__2);
				setState(407);
				table_name();
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(409);
				table_name();
				setState(410);
				match(K_NATURAL);
				setState(412);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_INNER) {
					{
					setState(411);
					match(K_INNER);
					}
				}

				setState(414);
				match(K_JOIN);
				setState(415);
				table_name();
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(417);
				table_name();
				setState(419);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_INNER) {
					{
					setState(418);
					match(K_INNER);
					}
				}

				setState(421);
				match(K_JOIN);
				setState(422);
				table_name();
				setState(423);
				match(K_ON);
				setState(424);
				on_content();
				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				{
				setState(426);
				table_name();
				setState(427);
				_la = _input.LA(1);
				if ( !(((((_la - 45)) & ~0x3f) == 0 && ((1L << (_la - 45)) & ((1L << (K_FULL - 45)) | (1L << (K_LEFT - 45)) | (1L << (K_RIGHT - 45)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(429);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_OUTER) {
					{
					setState(428);
					match(K_OUTER);
					}
				}

				setState(431);
				match(K_JOIN);
				setState(432);
				table_name();
				setState(433);
				match(K_ON);
				setState(434);
				on_content();
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class On_contentContext extends ParserRuleContext {
		public List<Column_full_nameContext> column_full_name() {
			return getRuleContexts(Column_full_nameContext.class);
		}
		public Column_full_nameContext column_full_name(int i) {
			return getRuleContext(Column_full_nameContext.class,i);
		}
		public List<TerminalNode> EQ() { return getTokens(SQLParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(SQLParser.EQ, i);
		}
		public List<TerminalNode> K_AND() { return getTokens(SQLParser.K_AND); }
		public TerminalNode K_AND(int i) {
			return getToken(SQLParser.K_AND, i);
		}
		public List<TerminalNode> AND() { return getTokens(SQLParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(SQLParser.AND, i);
		}
		public On_contentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_on_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterOn_content(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitOn_content(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitOn_content(this);
			else return visitor.visitChildren(this);
		}
	}

	public final On_contentContext on_content() throws RecognitionException {
		On_contentContext _localctx = new On_contentContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_on_content);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(438);
			column_full_name();
			setState(439);
			match(EQ);
			setState(440);
			column_full_name();
			setState(448);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==K_AND) {
				{
				{
				setState(441);
				_la = _input.LA(1);
				if ( !(_la==AND || _la==K_AND) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(442);
				column_full_name();
				setState(443);
				match(EQ);
				setState(444);
				column_full_name();
				}
				}
				setState(450);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Create_view_stmtContext extends ParserRuleContext {
		public TerminalNode K_CREATE() { return getToken(SQLParser.K_CREATE, 0); }
		public TerminalNode K_VIEW() { return getToken(SQLParser.K_VIEW, 0); }
		public View_nameContext view_name() {
			return getRuleContext(View_nameContext.class,0);
		}
		public TerminalNode K_AS() { return getToken(SQLParser.K_AS, 0); }
		public Select_stmtContext select_stmt() {
			return getRuleContext(Select_stmtContext.class,0);
		}
		public Create_view_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_create_view_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCreate_view_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCreate_view_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCreate_view_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Create_view_stmtContext create_view_stmt() throws RecognitionException {
		Create_view_stmtContext _localctx = new Create_view_stmtContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_create_view_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(451);
			match(K_CREATE);
			setState(452);
			match(K_VIEW);
			setState(453);
			view_name();
			setState(454);
			match(K_AS);
			setState(455);
			select_stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Drop_view_stmtContext extends ParserRuleContext {
		public TerminalNode K_DROP() { return getToken(SQLParser.K_DROP, 0); }
		public TerminalNode K_VIEW() { return getToken(SQLParser.K_VIEW, 0); }
		public View_nameContext view_name() {
			return getRuleContext(View_nameContext.class,0);
		}
		public TerminalNode K_IF() { return getToken(SQLParser.K_IF, 0); }
		public TerminalNode K_EXISTS() { return getToken(SQLParser.K_EXISTS, 0); }
		public Drop_view_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_drop_view_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDrop_view_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDrop_view_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDrop_view_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Drop_view_stmtContext drop_view_stmt() throws RecognitionException {
		Drop_view_stmtContext _localctx = new Drop_view_stmtContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_drop_view_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(457);
			match(K_DROP);
			setState(458);
			match(K_VIEW);
			setState(461);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==K_IF) {
				{
				setState(459);
				match(K_IF);
				setState(460);
				match(K_EXISTS);
				}
			}

			setState(463);
			view_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Update_stmtContext extends ParserRuleContext {
		public TerminalNode K_UPDATE() { return getToken(SQLParser.K_UPDATE, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public TerminalNode K_SET() { return getToken(SQLParser.K_SET, 0); }
		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class,0);
		}
		public TerminalNode EQ() { return getToken(SQLParser.EQ, 0); }
		public Literal_valueContext literal_value() {
			return getRuleContext(Literal_valueContext.class,0);
		}
		public TerminalNode K_WHERE() { return getToken(SQLParser.K_WHERE, 0); }
		public Multiple_conditionContext multiple_condition() {
			return getRuleContext(Multiple_conditionContext.class,0);
		}
		public Update_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_update_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterUpdate_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitUpdate_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUpdate_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Update_stmtContext update_stmt() throws RecognitionException {
		Update_stmtContext _localctx = new Update_stmtContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_update_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			match(K_UPDATE);
			setState(466);
			table_name();
			setState(467);
			match(K_SET);
			setState(468);
			column_name();
			setState(469);
			match(EQ);
			setState(470);
			literal_value();
			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==K_WHERE) {
				{
				setState(471);
				match(K_WHERE);
				setState(472);
				multiple_condition(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_defContext extends ParserRuleContext {
		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class,0);
		}
		public Type_nameContext type_name() {
			return getRuleContext(Type_nameContext.class,0);
		}
		public TerminalNode K_PRIMARY() { return getToken(SQLParser.K_PRIMARY, 0); }
		public TerminalNode K_KEY() { return getToken(SQLParser.K_KEY, 0); }
		public TerminalNode K_NOT() { return getToken(SQLParser.K_NOT, 0); }
		public TerminalNode K_NULL() { return getToken(SQLParser.K_NULL, 0); }
		public Column_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterColumn_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitColumn_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitColumn_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Column_defContext column_def() throws RecognitionException {
		Column_defContext _localctx = new Column_defContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_column_def);
		int _la;
		try {
			setState(495);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(475);
				column_name();
				setState(476);
				type_name();
				setState(479);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_PRIMARY) {
					{
					setState(477);
					match(K_PRIMARY);
					setState(478);
					match(K_KEY);
					}
				}

				setState(483);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_NOT) {
					{
					setState(481);
					match(K_NOT);
					setState(482);
					match(K_NULL);
					}
				}

				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(485);
				column_name();
				setState(486);
				type_name();
				setState(489);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_NOT) {
					{
					setState(487);
					match(K_NOT);
					setState(488);
					match(K_NULL);
					}
				}

				setState(493);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==K_PRIMARY) {
					{
					setState(491);
					match(K_PRIMARY);
					setState(492);
					match(K_KEY);
					}
				}

				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_nameContext extends ParserRuleContext {
		public TerminalNode T_INT() { return getToken(SQLParser.T_INT, 0); }
		public TerminalNode T_LONG() { return getToken(SQLParser.T_LONG, 0); }
		public TerminalNode T_FLOAT() { return getToken(SQLParser.T_FLOAT, 0); }
		public TerminalNode T_DOUBLE() { return getToken(SQLParser.T_DOUBLE, 0); }
		public TerminalNode T_STRING() { return getToken(SQLParser.T_STRING, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(SQLParser.INTEGER_LITERAL, 0); }
		public Type_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterType_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitType_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitType_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_nameContext type_name() throws RecognitionException {
		Type_nameContext _localctx = new Type_nameContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_type_name);
		try {
			setState(505);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T_INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(497);
				match(T_INT);
				}
				break;
			case T_LONG:
				enterOuterAlt(_localctx, 2);
				{
				setState(498);
				match(T_LONG);
				}
				break;
			case T_FLOAT:
				enterOuterAlt(_localctx, 3);
				{
				setState(499);
				match(T_FLOAT);
				}
				break;
			case T_DOUBLE:
				enterOuterAlt(_localctx, 4);
				{
				setState(500);
				match(T_DOUBLE);
				}
				break;
			case T_STRING:
				enterOuterAlt(_localctx, 5);
				{
				setState(501);
				match(T_STRING);
				setState(502);
				match(T__1);
				setState(503);
				match(INTEGER_LITERAL);
				setState(504);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Multiple_conditionContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public List<Multiple_conditionContext> multiple_condition() {
			return getRuleContexts(Multiple_conditionContext.class);
		}
		public Multiple_conditionContext multiple_condition(int i) {
			return getRuleContext(Multiple_conditionContext.class,i);
		}
		public TerminalNode AND() { return getToken(SQLParser.AND, 0); }
		public TerminalNode K_AND() { return getToken(SQLParser.K_AND, 0); }
		public TerminalNode OR() { return getToken(SQLParser.OR, 0); }
		public TerminalNode K_OR() { return getToken(SQLParser.K_OR, 0); }
		public Multiple_conditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterMultiple_condition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitMultiple_condition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitMultiple_condition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiple_conditionContext multiple_condition() throws RecognitionException {
		return multiple_condition(0);
	}

	private Multiple_conditionContext multiple_condition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Multiple_conditionContext _localctx = new Multiple_conditionContext(_ctx, _parentState);
		Multiple_conditionContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_multiple_condition, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(513);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				{
				setState(508);
				condition();
				}
				break;
			case 2:
				{
				setState(509);
				match(T__1);
				setState(510);
				multiple_condition(0);
				setState(511);
				match(T__3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(523);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(521);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
					case 1:
						{
						_localctx = new Multiple_conditionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiple_condition);
						setState(515);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(516);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==K_AND) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(517);
						multiple_condition(3);
						}
						break;
					case 2:
						{
						_localctx = new Multiple_conditionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiple_condition);
						setState(518);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(519);
						_la = _input.LA(1);
						if ( !(_la==OR || _la==K_OR) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(520);
						multiple_condition(2);
						}
						break;
					}
					} 
				}
				setState(525);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public List<ComparerContext> comparer() {
			return getRuleContexts(ComparerContext.class);
		}
		public ComparerContext comparer(int i) {
			return getRuleContext(ComparerContext.class,i);
		}
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public Column_full_nameContext column_full_name() {
			return getRuleContext(Column_full_nameContext.class,0);
		}
		public TerminalNode K_IS() { return getToken(SQLParser.K_IS, 0); }
		public TerminalNode K_NULL() { return getToken(SQLParser.K_NULL, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_condition);
		try {
			setState(535);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(527);
				comparer();
				setState(528);
				comparator();
				setState(529);
				comparer();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(531);
				column_full_name();
				setState(532);
				match(K_IS);
				setState(533);
				match(K_NULL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparerContext extends ParserRuleContext {
		public Column_full_nameContext column_full_name() {
			return getRuleContext(Column_full_nameContext.class,0);
		}
		public Literal_valueContext literal_value() {
			return getRuleContext(Literal_valueContext.class,0);
		}
		public ComparerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterComparer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitComparer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitComparer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparerContext comparer() throws RecognitionException {
		ComparerContext _localctx = new ComparerContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_comparer);
		try {
			setState(539);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(537);
				column_full_name();
				}
				break;
			case K_NULL:
			case INTEGER_LITERAL:
			case FLOAT_LITERAL:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(538);
				literal_value();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparatorContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(SQLParser.EQ, 0); }
		public TerminalNode NE() { return getToken(SQLParser.NE, 0); }
		public TerminalNode LE() { return getToken(SQLParser.LE, 0); }
		public TerminalNode GE() { return getToken(SQLParser.GE, 0); }
		public TerminalNode LT() { return getToken(SQLParser.LT, 0); }
		public TerminalNode GT() { return getToken(SQLParser.GT, 0); }
		public ComparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterComparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitComparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitComparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparatorContext comparator() throws RecognitionException {
		ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_comparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(541);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NE) | (1L << LT) | (1L << GT) | (1L << LE) | (1L << GE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_constraintContext extends ParserRuleContext {
		public TerminalNode K_PRIMARY() { return getToken(SQLParser.K_PRIMARY, 0); }
		public TerminalNode K_KEY() { return getToken(SQLParser.K_KEY, 0); }
		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class,0);
		}
		public Table_constraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterTable_constraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitTable_constraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitTable_constraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Table_constraintContext table_constraint() throws RecognitionException {
		Table_constraintContext _localctx = new Table_constraintContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_table_constraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(543);
			match(K_PRIMARY);
			setState(544);
			match(K_KEY);
			setState(545);
			match(T__1);
			setState(546);
			column_name();
			setState(547);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Result_columnContext extends ParserRuleContext {
		public TerminalNode MUL() { return getToken(SQLParser.MUL, 0); }
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public Column_full_nameContext column_full_name() {
			return getRuleContext(Column_full_nameContext.class,0);
		}
		public Result_columnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_result_column; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterResult_column(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitResult_column(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitResult_column(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Result_columnContext result_column() throws RecognitionException {
		Result_columnContext _localctx = new Result_columnContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_result_column);
		try {
			setState(555);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(549);
				match(MUL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(550);
				table_name();
				setState(551);
				match(T__4);
				setState(552);
				match(MUL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(554);
				column_full_name();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Auth_levelContext extends ParserRuleContext {
		public TerminalNode K_SELECT() { return getToken(SQLParser.K_SELECT, 0); }
		public TerminalNode K_INSERT() { return getToken(SQLParser.K_INSERT, 0); }
		public TerminalNode K_UPDATE() { return getToken(SQLParser.K_UPDATE, 0); }
		public TerminalNode K_DELETE() { return getToken(SQLParser.K_DELETE, 0); }
		public TerminalNode K_DROP() { return getToken(SQLParser.K_DROP, 0); }
		public Auth_levelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_auth_level; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterAuth_level(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitAuth_level(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitAuth_level(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Auth_levelContext auth_level() throws RecognitionException {
		Auth_levelContext _localctx = new Auth_levelContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_auth_level);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(557);
			_la = _input.LA(1);
			if ( !(((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & ((1L << (K_DELETE - 39)) | (1L << (K_DROP - 39)) | (1L << (K_INSERT - 39)) | (1L << (K_SELECT - 39)) | (1L << (K_UPDATE - 39)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Literal_valueContext extends ParserRuleContext {
		public TerminalNode FLOAT_LITERAL() { return getToken(SQLParser.FLOAT_LITERAL, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(SQLParser.INTEGER_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(SQLParser.STRING_LITERAL, 0); }
		public TerminalNode K_NULL() { return getToken(SQLParser.K_NULL, 0); }
		public Literal_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterLiteral_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitLiteral_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitLiteral_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Literal_valueContext literal_value() throws RecognitionException {
		Literal_valueContext _localctx = new Literal_valueContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_literal_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(559);
			_la = _input.LA(1);
			if ( !(((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (K_NULL - 60)) | (1L << (INTEGER_LITERAL - 60)) | (1L << (FLOAT_LITERAL - 60)) | (1L << (STRING_LITERAL - 60)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_full_nameContext extends ParserRuleContext {
		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class,0);
		}
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public Column_full_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_full_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterColumn_full_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitColumn_full_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitColumn_full_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Column_full_nameContext column_full_name() throws RecognitionException {
		Column_full_nameContext _localctx = new Column_full_nameContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_column_full_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(564);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				{
				setState(561);
				table_name();
				setState(562);
				match(T__4);
				}
				break;
			}
			setState(566);
			column_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Database_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SQLParser.IDENTIFIER, 0); }
		public Database_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_database_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDatabase_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDatabase_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDatabase_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Database_nameContext database_name() throws RecognitionException {
		Database_nameContext _localctx = new Database_nameContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_database_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(568);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SQLParser.IDENTIFIER, 0); }
		public Table_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterTable_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitTable_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitTable_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Table_nameContext table_name() throws RecognitionException {
		Table_nameContext _localctx = new Table_nameContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_table_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(570);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class User_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SQLParser.IDENTIFIER, 0); }
		public User_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_user_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterUser_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitUser_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUser_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final User_nameContext user_name() throws RecognitionException {
		User_nameContext _localctx = new User_nameContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_user_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(572);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SQLParser.IDENTIFIER, 0); }
		public Column_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterColumn_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitColumn_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitColumn_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Column_nameContext column_name() throws RecognitionException {
		Column_nameContext _localctx = new Column_nameContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_column_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(574);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class View_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SQLParser.IDENTIFIER, 0); }
		public View_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_view_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterView_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitView_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitView_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final View_nameContext view_name() throws RecognitionException {
		View_nameContext _localctx = new View_nameContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_view_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(576);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Savepoint_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SQLParser.IDENTIFIER, 0); }
		public Savepoint_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_savepoint_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSavepoint_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSavepoint_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSavepoint_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Savepoint_nameContext savepoint_name() throws RecognitionException {
		Savepoint_nameContext _localctx = new Savepoint_nameContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_savepoint_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(578);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PasswordContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(SQLParser.STRING_LITERAL, 0); }
		public PasswordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_password; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterPassword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitPassword(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitPassword(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PasswordContext password() throws RecognitionException {
		PasswordContext _localctx = new PasswordContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_password);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(580);
			match(STRING_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Numeric_valueContext extends ParserRuleContext {
		public TerminalNode INTEGER_LITERAL() { return getToken(SQLParser.INTEGER_LITERAL, 0); }
		public TerminalNode FLOAT_LITERAL() { return getToken(SQLParser.FLOAT_LITERAL, 0); }
		public Numeric_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numeric_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterNumeric_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitNumeric_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitNumeric_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Numeric_valueContext numeric_value() throws RecognitionException {
		Numeric_valueContext _localctx = new Numeric_valueContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_numeric_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(582);
			_la = _input.LA(1);
			if ( !(_la==INTEGER_LITERAL || _la==FLOAT_LITERAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 36:
			return multiple_condition_sempred((Multiple_conditionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean multiple_condition_sempred(Multiple_conditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3^\u024b\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\3\2\3\2\3\3\7\3p\n\3\f\3\16\3s\13\3\3\3\3\3\6"+
		"\3w\n\3\r\3\16\3x\3\3\7\3|\n\3\f\3\16\3\177\13\3\3\3\7\3\u0082\n\3\f\3"+
		"\16\3\u0085\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u00a0\n\4\3\5\3\5\3"+
		"\6\3\6\3\6\3\7\3\7\5\7\u00a9\n\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00bf\n\n\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\5\f\u00c9\n\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\5\16\u00d8\n\16\3\16\3\16\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\7\17\u00e3\n\17\f\17\16\17\u00e6\13\17\3\17\3\17\5\17\u00ea"+
		"\n\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\7\21\u00f6\n\21"+
		"\f\21\16\21\u00f9\13\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\7"+
		"\22\u0104\n\22\f\22\16\22\u0107\13\22\3\22\3\22\3\22\3\22\3\22\3\23\3"+
		"\23\3\23\3\24\3\24\3\24\3\24\3\24\5\24\u0116\n\24\3\25\3\25\3\25\3\25"+
		"\5\25\u011c\n\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0130\n\31\f\31\16\31\u0133\13"+
		"\31\3\31\3\31\5\31\u0137\n\31\3\31\3\31\3\31\3\31\7\31\u013d\n\31\f\31"+
		"\16\31\u0140\13\31\3\32\3\32\3\32\3\32\7\32\u0146\n\32\f\32\16\32\u0149"+
		"\13\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0153\n\33\3\33\3"+
		"\33\3\33\3\33\3\33\7\33\u015a\n\33\f\33\16\33\u015d\13\33\3\33\5\33\u0160"+
		"\n\33\5\33\u0162\n\33\3\34\5\34\u0165\n\34\3\34\3\34\3\34\7\34\u016a\n"+
		"\34\f\34\16\34\u016d\13\34\3\34\5\34\u0170\n\34\3\34\3\34\3\34\7\34\u0175"+
		"\n\34\f\34\16\34\u0178\13\34\5\34\u017a\n\34\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u018a\n\35\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u0195\n\36\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\5\37\u019f\n\37\3\37\3\37\3\37\3\37\3\37\5\37\u01a6"+
		"\n\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u01b0\n\37\3\37\3\37"+
		"\3\37\3\37\3\37\5\37\u01b7\n\37\3 \3 \3 \3 \3 \3 \3 \3 \7 \u01c1\n \f"+
		" \16 \u01c4\13 \3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\5\"\u01d0\n\"\3\"\3"+
		"\"\3#\3#\3#\3#\3#\3#\3#\3#\5#\u01dc\n#\3$\3$\3$\3$\5$\u01e2\n$\3$\3$\5"+
		"$\u01e6\n$\3$\3$\3$\3$\5$\u01ec\n$\3$\3$\5$\u01f0\n$\5$\u01f2\n$\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\5%\u01fc\n%\3&\3&\3&\3&\3&\3&\5&\u0204\n&\3&\3&\3"+
		"&\3&\3&\3&\7&\u020c\n&\f&\16&\u020f\13&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\5\'\u021a\n\'\3(\3(\5(\u021e\n(\3)\3)\3*\3*\3*\3*\3*\3*\3+\3+\3"+
		"+\3+\3+\3+\5+\u022e\n+\3,\3,\3-\3-\3.\3.\3.\5.\u0237\n.\3.\3.\3/\3/\3"+
		"\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3"+
		"\66\2\3J\67\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66"+
		"8:<>@BDFHJLNPRTVXZ\\^`bdfhj\2\r\4\2\36\36**\4\2\32\32++\3\2\16\21\6\2"+
		"\37\37%%:;LL\5\2//99FF\4\2\22\22\34\34\4\2\23\23@@\3\2\b\r\7\2)),,\65"+
		"\65IIPP\5\2>>WX[[\3\2WX\2\u026a\2l\3\2\2\2\4q\3\2\2\2\6\u009f\3\2\2\2"+
		"\b\u00a1\3\2\2\2\n\u00a3\3\2\2\2\f\u00a6\3\2\2\2\16\u00aa\3\2\2\2\20\u00ac"+
		"\3\2\2\2\22\u00af\3\2\2\2\24\u00c0\3\2\2\2\26\u00c4\3\2\2\2\30\u00cc\3"+
		"\2\2\2\32\u00d3\3\2\2\2\34\u00db\3\2\2\2\36\u00ed\3\2\2\2 \u00f1\3\2\2"+
		"\2\"\u00ff\3\2\2\2$\u010d\3\2\2\2&\u0110\3\2\2\2(\u0117\3\2\2\2*\u011f"+
		"\3\2\2\2,\u0122\3\2\2\2.\u0124\3\2\2\2\60\u0128\3\2\2\2\62\u0141\3\2\2"+
		"\2\64\u014c\3\2\2\2\66\u0179\3\2\2\28\u0189\3\2\2\2:\u0194\3\2\2\2<\u01b6"+
		"\3\2\2\2>\u01b8\3\2\2\2@\u01c5\3\2\2\2B\u01cb\3\2\2\2D\u01d3\3\2\2\2F"+
		"\u01f1\3\2\2\2H\u01fb\3\2\2\2J\u0203\3\2\2\2L\u0219\3\2\2\2N\u021d\3\2"+
		"\2\2P\u021f\3\2\2\2R\u0221\3\2\2\2T\u022d\3\2\2\2V\u022f\3\2\2\2X\u0231"+
		"\3\2\2\2Z\u0236\3\2\2\2\\\u023a\3\2\2\2^\u023c\3\2\2\2`\u023e\3\2\2\2"+
		"b\u0240\3\2\2\2d\u0242\3\2\2\2f\u0244\3\2\2\2h\u0246\3\2\2\2j\u0248\3"+
		"\2\2\2lm\5\4\3\2m\3\3\2\2\2np\7\3\2\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr"+
		"\3\2\2\2rt\3\2\2\2sq\3\2\2\2t}\5\6\4\2uw\7\3\2\2vu\3\2\2\2wx\3\2\2\2x"+
		"v\3\2\2\2xy\3\2\2\2yz\3\2\2\2z|\5\6\4\2{v\3\2\2\2|\177\3\2\2\2}{\3\2\2"+
		"\2}~\3\2\2\2~\u0083\3\2\2\2\177}\3\2\2\2\u0080\u0082\7\3\2\2\u0081\u0080"+
		"\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"\5\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u00a0\5\22\n\2\u0087\u00a0\5\34\17"+
		"\2\u0088\u00a0\5\24\13\2\u0089\u00a0\5\30\r\2\u008a\u00a0\5\26\f\2\u008b"+
		"\u00a0\5\32\16\2\u008c\u00a0\5&\24\2\u008d\u00a0\5(\25\2\u008e\u00a0\5"+
		"\60\31\2\u008f\u00a0\5\64\33\2\u0090\u00a0\5@!\2\u0091\u00a0\5B\"\2\u0092"+
		"\u00a0\5 \21\2\u0093\u00a0\5\"\22\2\u0094\u00a0\5$\23\2\u0095\u00a0\5"+
		"*\26\2\u0096\u00a0\5.\30\2\u0097\u00a0\5\36\20\2\u0098\u00a0\5,\27\2\u0099"+
		"\u00a0\5D#\2\u009a\u00a0\5\20\t\2\u009b\u00a0\5\16\b\2\u009c\u00a0\5\f"+
		"\7\2\u009d\u00a0\5\n\6\2\u009e\u00a0\5\b\5\2\u009f\u0086\3\2\2\2\u009f"+
		"\u0087\3\2\2\2\u009f\u0088\3\2\2\2\u009f\u0089\3\2\2\2\u009f\u008a\3\2"+
		"\2\2\u009f\u008b\3\2\2\2\u009f\u008c\3\2\2\2\u009f\u008d\3\2\2\2\u009f"+
		"\u008e\3\2\2\2\u009f\u008f\3\2\2\2\u009f\u0090\3\2\2\2\u009f\u0091\3\2"+
		"\2\2\u009f\u0092\3\2\2\2\u009f\u0093\3\2\2\2\u009f\u0094\3\2\2\2\u009f"+
		"\u0095\3\2\2\2\u009f\u0096\3\2\2\2\u009f\u0097\3\2\2\2\u009f\u0098\3\2"+
		"\2\2\u009f\u0099\3\2\2\2\u009f\u009a\3\2\2\2\u009f\u009b\3\2\2\2\u009f"+
		"\u009c\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u009e\3\2\2\2\u00a0\7\3\2\2\2"+
		"\u00a1\u00a2\7\"\2\2\u00a2\t\3\2\2\2\u00a3\u00a4\7H\2\2\u00a4\u00a5\5"+
		"f\64\2\u00a5\13\3\2\2\2\u00a6\u00a8\7G\2\2\u00a7\u00a9\5f\64\2\u00a8\u00a7"+
		"\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\r\3\2\2\2\u00aa\u00ab\7$\2\2\u00ab"+
		"\17\3\2\2\2\u00ac\u00ad\7 \2\2\u00ad\u00ae\7O\2\2\u00ae\21\3\2\2\2\u00af"+
		"\u00b0\7\33\2\2\u00b0\u00b1\7M\2\2\u00b1\u00be\5^\60\2\u00b2\u00b3\7\31"+
		"\2\2\u00b3\u00b4\5b\62\2\u00b4\u00b5\5H%\2\u00b5\u00bf\3\2\2\2\u00b6\u00b7"+
		"\7,\2\2\u00b7\u00b8\7#\2\2\u00b8\u00bf\5b\62\2\u00b9\u00ba\7\33\2\2\u00ba"+
		"\u00bb\7#\2\2\u00bb\u00bc\5b\62\2\u00bc\u00bd\5H%\2\u00bd\u00bf\3\2\2"+
		"\2\u00be\u00b2\3\2\2\2\u00be\u00b6\3\2\2\2\u00be\u00b9\3\2\2\2\u00bf\23"+
		"\3\2\2\2\u00c0\u00c1\7&\2\2\u00c1\u00c2\7\'\2\2\u00c2\u00c3\5\\/\2\u00c3"+
		"\25\3\2\2\2\u00c4\u00c5\7,\2\2\u00c5\u00c8\7\'\2\2\u00c6\u00c7\7\61\2"+
		"\2\u00c7\u00c9\7-\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00ca"+
		"\3\2\2\2\u00ca\u00cb\5\\/\2\u00cb\27\3\2\2\2\u00cc\u00cd\7&\2\2\u00cd"+
		"\u00ce\7R\2\2\u00ce\u00cf\5`\61\2\u00cf\u00d0\7\63\2\2\u00d0\u00d1\7!"+
		"\2\2\u00d1\u00d2\5h\65\2\u00d2\31\3\2\2\2\u00d3\u00d4\7,\2\2\u00d4\u00d7"+
		"\7R\2\2\u00d5\u00d6\7\61\2\2\u00d6\u00d8\7-\2\2\u00d7\u00d5\3\2\2\2\u00d7"+
		"\u00d8\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\5`\61\2\u00da\33\3\2\2"+
		"\2\u00db\u00dc\7&\2\2\u00dc\u00dd\7M\2\2\u00dd\u00de\5^\60\2\u00de\u00df"+
		"\7\4\2\2\u00df\u00e4\5F$\2\u00e0\u00e1\7\5\2\2\u00e1\u00e3\5F$\2\u00e2"+
		"\u00e0\3\2\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2"+
		"\2\2\u00e5\u00e9\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00e8\7\5\2\2\u00e8"+
		"\u00ea\5R*\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00eb\3\2\2"+
		"\2\u00eb\u00ec\7\6\2\2\u00ec\35\3\2\2\2\u00ed\u00ee\7K\2\2\u00ee\u00ef"+
		"\7M\2\2\u00ef\u00f0\5^\60\2\u00f0\37\3\2\2\2\u00f1\u00f2\7\60\2\2\u00f2"+
		"\u00f7\5V,\2\u00f3\u00f4\7\5\2\2\u00f4\u00f6\5V,\2\u00f5\u00f3\3\2\2\2"+
		"\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00fa"+
		"\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa\u00fb\7?\2\2\u00fb\u00fc\5^\60\2\u00fc"+
		"\u00fd\7N\2\2\u00fd\u00fe\5`\61\2\u00fe!\3\2\2\2\u00ff\u0100\7E\2\2\u0100"+
		"\u0105\5V,\2\u0101\u0102\7\5\2\2\u0102\u0104\5V,\2\u0103\u0101\3\2\2\2"+
		"\u0104\u0107\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0108"+
		"\3\2\2\2\u0107\u0105\3\2\2\2\u0108\u0109\7?\2\2\u0109\u010a\5^\60\2\u010a"+
		"\u010b\7.\2\2\u010b\u010c\5`\61\2\u010c#\3\2\2\2\u010d\u010e\7Q\2\2\u010e"+
		"\u010f\5\\/\2\u010f%\3\2\2\2\u0110\u0111\7)\2\2\u0111\u0112\7.\2\2\u0112"+
		"\u0115\5^\60\2\u0113\u0114\7U\2\2\u0114\u0116\5J&\2\u0115\u0113\3\2\2"+
		"\2\u0115\u0116\3\2\2\2\u0116\'\3\2\2\2\u0117\u0118\7,\2\2\u0118\u011b"+
		"\7M\2\2\u0119\u011a\7\61\2\2\u011a\u011c\7-\2\2\u011b\u0119\3\2\2\2\u011b"+
		"\u011c\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011e\5^\60\2\u011e)\3\2\2\2"+
		"\u011f\u0120\7K\2\2\u0120\u0121\7(\2\2\u0121+\3\2\2\2\u0122\u0123\7D\2"+
		"\2\u0123-\3\2\2\2\u0124\u0125\7K\2\2\u0125\u0126\7M\2\2\u0126\u0127\5"+
		"^\60\2\u0127/\3\2\2\2\u0128\u0129\7\65\2\2\u0129\u012a\7\66\2\2\u012a"+
		"\u0136\5^\60\2\u012b\u012c\7\4\2\2\u012c\u0131\5b\62\2\u012d\u012e\7\5"+
		"\2\2\u012e\u0130\5b\62\2\u012f\u012d\3\2\2\2\u0130\u0133\3\2\2\2\u0131"+
		"\u012f\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0134\3\2\2\2\u0133\u0131\3\2"+
		"\2\2\u0134\u0135\7\6\2\2\u0135\u0137\3\2\2\2\u0136\u012b\3\2\2\2\u0136"+
		"\u0137\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139\7S\2\2\u0139\u013e\5\62"+
		"\32\2\u013a\u013b\7\5\2\2\u013b\u013d\5\62\32\2\u013c\u013a\3\2\2\2\u013d"+
		"\u0140\3\2\2\2\u013e\u013c\3\2\2\2\u013e\u013f\3\2\2\2\u013f\61\3\2\2"+
		"\2\u0140\u013e\3\2\2\2\u0141\u0142\7\4\2\2\u0142\u0147\5X-\2\u0143\u0144"+
		"\7\5\2\2\u0144\u0146\5X-\2\u0145\u0143\3\2\2\2\u0146\u0149\3\2\2\2\u0147"+
		"\u0145\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u014a\3\2\2\2\u0149\u0147\3\2"+
		"\2\2\u014a\u014b\7\6\2\2\u014b\63\3\2\2\2\u014c\u014d\7I\2\2\u014d\u014e"+
		"\5\66\34\2\u014e\u014f\7.\2\2\u014f\u0152\5<\37\2\u0150\u0151\7U\2\2\u0151"+
		"\u0153\5J&\2\u0152\u0150\3\2\2\2\u0152\u0153\3\2\2\2\u0153\u0161\3\2\2"+
		"\2\u0154\u0155\7A\2\2\u0155\u0156\7!\2\2\u0156\u015b\5Z.\2\u0157\u0158"+
		"\7\5\2\2\u0158\u015a\5Z.\2\u0159\u0157\3\2\2\2\u015a\u015d\3\2\2\2\u015b"+
		"\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\u015f\3\2\2\2\u015d\u015b\3\2"+
		"\2\2\u015e\u0160\t\2\2\2\u015f\u015e\3\2\2\2\u015f\u0160\3\2\2\2\u0160"+
		"\u0162\3\2\2\2\u0161\u0154\3\2\2\2\u0161\u0162\3\2\2\2\u0162\65\3\2\2"+
		"\2\u0163\u0165\t\3\2\2\u0164\u0163\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0166"+
		"\3\2\2\2\u0166\u016b\58\35\2\u0167\u0168\7\5\2\2\u0168\u016a\58\35\2\u0169"+
		"\u0167\3\2\2\2\u016a\u016d\3\2\2\2\u016b\u0169\3\2\2\2\u016b\u016c\3\2"+
		"\2\2\u016c\u017a\3\2\2\2\u016d\u016b\3\2\2\2\u016e\u0170\t\3\2\2\u016f"+
		"\u016e\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0176\5:"+
		"\36\2\u0172\u0173\7\5\2\2\u0173\u0175\5:\36\2\u0174\u0172\3\2\2\2\u0175"+
		"\u0178\3\2\2\2\u0176\u0174\3\2\2\2\u0176\u0177\3\2\2\2\u0177\u017a\3\2"+
		"\2\2\u0178\u0176\3\2\2\2\u0179\u0164\3\2\2\2\u0179\u016f\3\2\2\2\u017a"+
		"\67\3\2\2\2\u017b\u018a\5j\66\2\u017c\u018a\5T+\2\u017d\u017e\5Z.\2\u017e"+
		"\u017f\t\4\2\2\u017f\u0180\5j\66\2\u0180\u018a\3\2\2\2\u0181\u0182\5j"+
		"\66\2\u0182\u0183\t\4\2\2\u0183\u0184\5j\66\2\u0184\u018a\3\2\2\2\u0185"+
		"\u0186\5j\66\2\u0186\u0187\t\4\2\2\u0187\u0188\5Z.\2\u0188\u018a\3\2\2"+
		"\2\u0189\u017b\3\2\2\2\u0189\u017c\3\2\2\2\u0189\u017d\3\2\2\2\u0189\u0181"+
		"\3\2\2\2\u0189\u0185\3\2\2\2\u018a9\3\2\2\2\u018b\u018c\t\5\2\2\u018c"+
		"\u018d\7\4\2\2\u018d\u018e\5Z.\2\u018e\u018f\7\6\2\2\u018f\u0195\3\2\2"+
		"\2\u0190\u0191\7%\2\2\u0191\u0192\7\4\2\2\u0192\u0193\7\20\2\2\u0193\u0195"+
		"\7\6\2\2\u0194\u018b\3\2\2\2\u0194\u0190\3\2\2\2\u0195;\3\2\2\2\u0196"+
		"\u01b7\5^\60\2\u0197\u0198\5^\60\2\u0198\u0199\7\5\2\2\u0199\u019a\5^"+
		"\60\2\u019a\u01b7\3\2\2\2\u019b\u019c\5^\60\2\u019c\u019e\7<\2\2\u019d"+
		"\u019f\7\64\2\2\u019e\u019d\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a0\3"+
		"\2\2\2\u01a0\u01a1\7\67\2\2\u01a1\u01a2\5^\60\2\u01a2\u01b7\3\2\2\2\u01a3"+
		"\u01a5\5^\60\2\u01a4\u01a6\7\64\2\2\u01a5\u01a4\3\2\2\2\u01a5\u01a6\3"+
		"\2\2\2\u01a6\u01a7\3\2\2\2\u01a7\u01a8\7\67\2\2\u01a8\u01a9\5^\60\2\u01a9"+
		"\u01aa\7?\2\2\u01aa\u01ab\5> \2\u01ab\u01b7\3\2\2\2\u01ac\u01ad\5^\60"+
		"\2\u01ad\u01af\t\6\2\2\u01ae\u01b0\7B\2\2\u01af\u01ae\3\2\2\2\u01af\u01b0"+
		"\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1\u01b2\7\67\2\2\u01b2\u01b3\5^\60\2"+
		"\u01b3\u01b4\7?\2\2\u01b4\u01b5\5> \2\u01b5\u01b7\3\2\2\2\u01b6\u0196"+
		"\3\2\2\2\u01b6\u0197\3\2\2\2\u01b6\u019b\3\2\2\2\u01b6\u01a3\3\2\2\2\u01b6"+
		"\u01ac\3\2\2\2\u01b7=\3\2\2\2\u01b8\u01b9\5Z.\2\u01b9\u01ba\7\b\2\2\u01ba"+
		"\u01c2\5Z.\2\u01bb\u01bc\t\7\2\2\u01bc\u01bd\5Z.\2\u01bd\u01be\7\b\2\2"+
		"\u01be\u01bf\5Z.\2\u01bf\u01c1\3\2\2\2\u01c0\u01bb\3\2\2\2\u01c1\u01c4"+
		"\3\2\2\2\u01c2\u01c0\3\2\2\2\u01c2\u01c3\3\2\2\2\u01c3?\3\2\2\2\u01c4"+
		"\u01c2\3\2\2\2\u01c5\u01c6\7&\2\2\u01c6\u01c7\7T\2\2\u01c7\u01c8\5d\63"+
		"\2\u01c8\u01c9\7\35\2\2\u01c9\u01ca\5\64\33\2\u01caA\3\2\2\2\u01cb\u01cc"+
		"\7,\2\2\u01cc\u01cf\7T\2\2\u01cd\u01ce\7\61\2\2\u01ce\u01d0\7-\2\2\u01cf"+
		"\u01cd\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01d2\5d"+
		"\63\2\u01d2C\3\2\2\2\u01d3\u01d4\7P\2\2\u01d4\u01d5\5^\60\2\u01d5\u01d6"+
		"\7J\2\2\u01d6\u01d7\5b\62\2\u01d7\u01d8\7\b\2\2\u01d8\u01db\5X-\2\u01d9"+
		"\u01da\7U\2\2\u01da\u01dc\5J&\2\u01db\u01d9\3\2\2\2\u01db\u01dc\3\2\2"+
		"\2\u01dcE\3\2\2\2\u01dd\u01de\5b\62\2\u01de\u01e1\5H%\2\u01df\u01e0\7"+
		"C\2\2\u01e0\u01e2\78\2\2\u01e1\u01df\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2"+
		"\u01e5\3\2\2\2\u01e3\u01e4\7=\2\2\u01e4\u01e6\7>\2\2\u01e5\u01e3\3\2\2"+
		"\2\u01e5\u01e6\3\2\2\2\u01e6\u01f2\3\2\2\2\u01e7\u01e8\5b\62\2\u01e8\u01eb"+
		"\5H%\2\u01e9\u01ea\7=\2\2\u01ea\u01ec\7>\2\2\u01eb\u01e9\3\2\2\2\u01eb"+
		"\u01ec\3\2\2\2\u01ec\u01ef\3\2\2\2\u01ed\u01ee\7C\2\2\u01ee\u01f0\78\2"+
		"\2\u01ef\u01ed\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0\u01f2\3\2\2\2\u01f1\u01dd"+
		"\3\2\2\2\u01f1\u01e7\3\2\2\2\u01f2G\3\2\2\2\u01f3\u01fc\7\24\2\2\u01f4"+
		"\u01fc\7\25\2\2\u01f5\u01fc\7\26\2\2\u01f6\u01fc\7\27\2\2\u01f7\u01f8"+
		"\7\30\2\2\u01f8\u01f9\7\4\2\2\u01f9\u01fa\7W\2\2\u01fa\u01fc\7\6\2\2\u01fb"+
		"\u01f3\3\2\2\2\u01fb\u01f4\3\2\2\2\u01fb\u01f5\3\2\2\2\u01fb\u01f6\3\2"+
		"\2\2\u01fb\u01f7\3\2\2\2\u01fcI\3\2\2\2\u01fd\u01fe\b&\1\2\u01fe\u0204"+
		"\5L\'\2\u01ff\u0200\7\4\2\2\u0200\u0201\5J&\2\u0201\u0202\7\6\2\2\u0202"+
		"\u0204\3\2\2\2\u0203\u01fd\3\2\2\2\u0203\u01ff\3\2\2\2\u0204\u020d\3\2"+
		"\2\2\u0205\u0206\f\4\2\2\u0206\u0207\t\7\2\2\u0207\u020c\5J&\5\u0208\u0209"+
		"\f\3\2\2\u0209\u020a\t\b\2\2\u020a\u020c\5J&\4\u020b\u0205\3\2\2\2\u020b"+
		"\u0208\3\2\2\2\u020c\u020f\3\2\2\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2"+
		"\2\2\u020eK\3\2\2\2\u020f\u020d\3\2\2\2\u0210\u021a\3\2\2\2\u0211\u0212"+
		"\5N(\2\u0212\u0213\5P)\2\u0213\u0214\5N(\2\u0214\u021a\3\2\2\2\u0215\u0216"+
		"\5Z.\2\u0216\u0217\7\62\2\2\u0217\u0218\7>\2\2\u0218\u021a\3\2\2\2\u0219"+
		"\u0210\3\2\2\2\u0219\u0211\3\2\2\2\u0219\u0215\3\2\2\2\u021aM\3\2\2\2"+
		"\u021b\u021e\5Z.\2\u021c\u021e\5X-\2\u021d\u021b\3\2\2\2\u021d\u021c\3"+
		"\2\2\2\u021eO\3\2\2\2\u021f\u0220\t\t\2\2\u0220Q\3\2\2\2\u0221\u0222\7"+
		"C\2\2\u0222\u0223\78\2\2\u0223\u0224\7\4\2\2\u0224\u0225\5b\62\2\u0225"+
		"\u0226\7\6\2\2\u0226S\3\2\2\2\u0227\u022e\7\20\2\2\u0228\u0229\5^\60\2"+
		"\u0229\u022a\7\7\2\2\u022a\u022b\7\20\2\2\u022b\u022e\3\2\2\2\u022c\u022e"+
		"\5Z.\2\u022d\u0227\3\2\2\2\u022d\u0228\3\2\2\2\u022d\u022c\3\2\2\2\u022e"+
		"U\3\2\2\2\u022f\u0230\t\n\2\2\u0230W\3\2\2\2\u0231\u0232\t\13\2\2\u0232"+
		"Y\3\2\2\2\u0233\u0234\5^\60\2\u0234\u0235\7\7\2\2\u0235\u0237\3\2\2\2"+
		"\u0236\u0233\3\2\2\2\u0236\u0237\3\2\2\2\u0237\u0238\3\2\2\2\u0238\u0239"+
		"\5b\62\2\u0239[\3\2\2\2\u023a\u023b\7V\2\2\u023b]\3\2\2\2\u023c\u023d"+
		"\7V\2\2\u023d_\3\2\2\2\u023e\u023f\7V\2\2\u023fa\3\2\2\2\u0240\u0241\7"+
		"V\2\2\u0241c\3\2\2\2\u0242\u0243\7V\2\2\u0243e\3\2\2\2\u0244\u0245\7V"+
		"\2\2\u0245g\3\2\2\2\u0246\u0247\7[\2\2\u0247i\3\2\2\2\u0248\u0249\t\f"+
		"\2\2\u0249k\3\2\2\2\64qx}\u0083\u009f\u00a8\u00be\u00c8\u00d7\u00e4\u00e9"+
		"\u00f7\u0105\u0115\u011b\u0131\u0136\u013e\u0147\u0152\u015b\u015f\u0161"+
		"\u0164\u016b\u016f\u0176\u0179\u0189\u0194\u019e\u01a5\u01af\u01b6\u01c2"+
		"\u01cf\u01db\u01e1\u01e5\u01eb\u01ef\u01f1\u01fb\u0203\u020b\u020d\u0219"+
		"\u021d\u022d\u0236";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}