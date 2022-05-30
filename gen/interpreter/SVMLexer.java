// Generated from C:/Users/dino9/Desktop/UNI/MAGISTRALE/COMPILATORI E INTERPRETI/Progetto/AssetLan/src/interpreter\SVM.g4 by ANTLR 4.9.2
package interpreter;

import java.util.HashMap;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		REGISTER=1, PUSH=2, ADDRESS=3, POP=4, ADD=5, ADDI=6, SUB=7, SUBI=8, MULT=9, 
		MULTI=10, DIV=11, DIVI=12, NOT=13, OR=14, AND=15, STOREW=16, LOADW=17, 
		MOVE=18, BRANCH=19, BCOND=20, LE=21, LT=22, EQ=23, GE=24, GT=25, JAL=26, 
		JR=27, PRINT=28, LOAD=29, HALT=30, COL=31, LPAR=32, RPAR=33, LABEL=34, 
		NUMBER=35, WHITESP=36, LINECOMMENTS=37, BLOCKCOMMENTS=38;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"REGISTER", "PUSH", "ADDRESS", "POP", "ADD", "ADDI", "SUB", "SUBI", "MULT", 
			"MULTI", "DIV", "DIVI", "NOT", "OR", "AND", "STOREW", "LOADW", "MOVE", 
			"BRANCH", "BCOND", "LE", "LT", "EQ", "GE", "GT", "JAL", "JR", "PRINT", 
			"LOAD", "HALT", "COL", "LPAR", "RPAR", "LABEL", "NUMBER", "WHITESP", 
			"LINECOMMENTS", "BLOCKCOMMENTS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'push'", "'address'", "'pop'", "'add'", "'addi'", "'sub'", 
			"'subi'", "'mult'", "'multi'", "'div'", "'divi'", "'not'", "'or'", "'and'", 
			"'sw'", "'lw'", "'mv'", "'b'", "'bc'", "'le'", "'lt'", "'eq'", "'ge'", 
			"'gt'", "'jal'", "'jr'", "'print'", "'li'", "'halt'", "':'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "REGISTER", "PUSH", "ADDRESS", "POP", "ADD", "ADDI", "SUB", "SUBI", 
			"MULT", "MULTI", "DIV", "DIVI", "NOT", "OR", "AND", "STOREW", "LOADW", 
			"MOVE", "BRANCH", "BCOND", "LE", "LT", "EQ", "GE", "GT", "JAL", "JR", 
			"PRINT", "LOAD", "HALT", "COL", "LPAR", "RPAR", "LABEL", "NUMBER", "WHITESP", 
			"LINECOMMENTS", "BLOCKCOMMENTS"
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


	public SVMLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2(\u0116\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2`\n\2\5\2b\n\2\3\3\3\3\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3"+
		"\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3"+
		"\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3"+
		"\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3"+
		"\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\7#\u00e0\n#\f#"+
		"\16#\u00e3\13#\3$\3$\5$\u00e7\n$\3$\3$\7$\u00eb\n$\f$\16$\u00ee\13$\5"+
		"$\u00f0\n$\3%\6%\u00f3\n%\r%\16%\u00f4\3%\3%\3&\3&\3&\3&\7&\u00fd\n&\f"+
		"&\16&\u0100\13&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\7\'\u010d\n"+
		"\'\f\'\16\'\u0110\13\'\3\'\3\'\3\'\3\'\3\'\2\2(\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(\3"+
		"\2\n\4\2cctt\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"\"\4\2\f\f\17\17\4"+
		"\2,,\61\61\3\2,,\3\2\61\61\2\u0125\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2"+
		"\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\3"+
		"O\3\2\2\2\5c\3\2\2\2\7h\3\2\2\2\tp\3\2\2\2\13t\3\2\2\2\rx\3\2\2\2\17}"+
		"\3\2\2\2\21\u0081\3\2\2\2\23\u0086\3\2\2\2\25\u008b\3\2\2\2\27\u0091\3"+
		"\2\2\2\31\u0095\3\2\2\2\33\u009a\3\2\2\2\35\u009e\3\2\2\2\37\u00a1\3\2"+
		"\2\2!\u00a5\3\2\2\2#\u00a8\3\2\2\2%\u00ab\3\2\2\2\'\u00ae\3\2\2\2)\u00b0"+
		"\3\2\2\2+\u00b3\3\2\2\2-\u00b6\3\2\2\2/\u00b9\3\2\2\2\61\u00bc\3\2\2\2"+
		"\63\u00bf\3\2\2\2\65\u00c2\3\2\2\2\67\u00c6\3\2\2\29\u00c9\3\2\2\2;\u00cf"+
		"\3\2\2\2=\u00d2\3\2\2\2?\u00d7\3\2\2\2A\u00d9\3\2\2\2C\u00db\3\2\2\2E"+
		"\u00dd\3\2\2\2G\u00ef\3\2\2\2I\u00f2\3\2\2\2K\u00f8\3\2\2\2M\u0103\3\2"+
		"\2\2Oa\7&\2\2PQ\t\2\2\2Qb\4\62;\2RS\7u\2\2S`\7r\2\2TU\7h\2\2U`\7r\2\2"+
		"VW\7j\2\2W`\7r\2\2XY\7d\2\2YZ\7u\2\2Z`\7r\2\2[\\\7c\2\2\\`\7n\2\2]^\7"+
		"t\2\2^`\7c\2\2_R\3\2\2\2_T\3\2\2\2_V\3\2\2\2_X\3\2\2\2_[\3\2\2\2_]\3\2"+
		"\2\2`b\3\2\2\2aP\3\2\2\2a_\3\2\2\2b\4\3\2\2\2cd\7r\2\2de\7w\2\2ef\7u\2"+
		"\2fg\7j\2\2g\6\3\2\2\2hi\7c\2\2ij\7f\2\2jk\7f\2\2kl\7t\2\2lm\7g\2\2mn"+
		"\7u\2\2no\7u\2\2o\b\3\2\2\2pq\7r\2\2qr\7q\2\2rs\7r\2\2s\n\3\2\2\2tu\7"+
		"c\2\2uv\7f\2\2vw\7f\2\2w\f\3\2\2\2xy\7c\2\2yz\7f\2\2z{\7f\2\2{|\7k\2\2"+
		"|\16\3\2\2\2}~\7u\2\2~\177\7w\2\2\177\u0080\7d\2\2\u0080\20\3\2\2\2\u0081"+
		"\u0082\7u\2\2\u0082\u0083\7w\2\2\u0083\u0084\7d\2\2\u0084\u0085\7k\2\2"+
		"\u0085\22\3\2\2\2\u0086\u0087\7o\2\2\u0087\u0088\7w\2\2\u0088\u0089\7"+
		"n\2\2\u0089\u008a\7v\2\2\u008a\24\3\2\2\2\u008b\u008c\7o\2\2\u008c\u008d"+
		"\7w\2\2\u008d\u008e\7n\2\2\u008e\u008f\7v\2\2\u008f\u0090\7k\2\2\u0090"+
		"\26\3\2\2\2\u0091\u0092\7f\2\2\u0092\u0093\7k\2\2\u0093\u0094\7x\2\2\u0094"+
		"\30\3\2\2\2\u0095\u0096\7f\2\2\u0096\u0097\7k\2\2\u0097\u0098\7x\2\2\u0098"+
		"\u0099\7k\2\2\u0099\32\3\2\2\2\u009a\u009b\7p\2\2\u009b\u009c\7q\2\2\u009c"+
		"\u009d\7v\2\2\u009d\34\3\2\2\2\u009e\u009f\7q\2\2\u009f\u00a0\7t\2\2\u00a0"+
		"\36\3\2\2\2\u00a1\u00a2\7c\2\2\u00a2\u00a3\7p\2\2\u00a3\u00a4\7f\2\2\u00a4"+
		" \3\2\2\2\u00a5\u00a6\7u\2\2\u00a6\u00a7\7y\2\2\u00a7\"\3\2\2\2\u00a8"+
		"\u00a9\7n\2\2\u00a9\u00aa\7y\2\2\u00aa$\3\2\2\2\u00ab\u00ac\7o\2\2\u00ac"+
		"\u00ad\7x\2\2\u00ad&\3\2\2\2\u00ae\u00af\7d\2\2\u00af(\3\2\2\2\u00b0\u00b1"+
		"\7d\2\2\u00b1\u00b2\7e\2\2\u00b2*\3\2\2\2\u00b3\u00b4\7n\2\2\u00b4\u00b5"+
		"\7g\2\2\u00b5,\3\2\2\2\u00b6\u00b7\7n\2\2\u00b7\u00b8\7v\2\2\u00b8.\3"+
		"\2\2\2\u00b9\u00ba\7g\2\2\u00ba\u00bb\7s\2\2\u00bb\60\3\2\2\2\u00bc\u00bd"+
		"\7i\2\2\u00bd\u00be\7g\2\2\u00be\62\3\2\2\2\u00bf\u00c0\7i\2\2\u00c0\u00c1"+
		"\7v\2\2\u00c1\64\3\2\2\2\u00c2\u00c3\7l\2\2\u00c3\u00c4\7c\2\2\u00c4\u00c5"+
		"\7n\2\2\u00c5\66\3\2\2\2\u00c6\u00c7\7l\2\2\u00c7\u00c8\7t\2\2\u00c88"+
		"\3\2\2\2\u00c9\u00ca\7r\2\2\u00ca\u00cb\7t\2\2\u00cb\u00cc\7k\2\2\u00cc"+
		"\u00cd\7p\2\2\u00cd\u00ce\7v\2\2\u00ce:\3\2\2\2\u00cf\u00d0\7n\2\2\u00d0"+
		"\u00d1\7k\2\2\u00d1<\3\2\2\2\u00d2\u00d3\7j\2\2\u00d3\u00d4\7c\2\2\u00d4"+
		"\u00d5\7n\2\2\u00d5\u00d6\7v\2\2\u00d6>\3\2\2\2\u00d7\u00d8\7<\2\2\u00d8"+
		"@\3\2\2\2\u00d9\u00da\7*\2\2\u00daB\3\2\2\2\u00db\u00dc\7+\2\2\u00dcD"+
		"\3\2\2\2\u00dd\u00e1\t\3\2\2\u00de\u00e0\t\4\2\2\u00df\u00de\3\2\2\2\u00e0"+
		"\u00e3\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2F\3\2\2\2"+
		"\u00e3\u00e1\3\2\2\2\u00e4\u00f0\7\62\2\2\u00e5\u00e7\7/\2\2\u00e6\u00e5"+
		"\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00ec\4\63;\2\u00e9"+
		"\u00eb\4\62;\2\u00ea\u00e9\3\2\2\2\u00eb\u00ee\3\2\2\2\u00ec\u00ea\3\2"+
		"\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00f0\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ef"+
		"\u00e4\3\2\2\2\u00ef\u00e6\3\2\2\2\u00f0H\3\2\2\2\u00f1\u00f3\t\5\2\2"+
		"\u00f2\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4\u00f5"+
		"\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f7\b%\2\2\u00f7J\3\2\2\2\u00f8\u00f9"+
		"\7\61\2\2\u00f9\u00fa\7\61\2\2\u00fa\u00fe\3\2\2\2\u00fb\u00fd\n\6\2\2"+
		"\u00fc\u00fb\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff"+
		"\3\2\2\2\u00ff\u0101\3\2\2\2\u0100\u00fe\3\2\2\2\u0101\u0102\b&\2\2\u0102"+
		"L\3\2\2\2\u0103\u0104\7\61\2\2\u0104\u0105\7,\2\2\u0105\u010e\3\2\2\2"+
		"\u0106\u010d\n\7\2\2\u0107\u0108\7\61\2\2\u0108\u010d\n\b\2\2\u0109\u010a"+
		"\7,\2\2\u010a\u010d\n\t\2\2\u010b\u010d\5M\'\2\u010c\u0106\3\2\2\2\u010c"+
		"\u0107\3\2\2\2\u010c\u0109\3\2\2\2\u010c\u010b\3\2\2\2\u010d\u0110\3\2"+
		"\2\2\u010e\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0111\3\2\2\2\u0110"+
		"\u010e\3\2\2\2\u0111\u0112\7,\2\2\u0112\u0113\7\61\2\2\u0113\u0114\3\2"+
		"\2\2\u0114\u0115\b\'\2\2\u0115N\3\2\2\2\r\2_a\u00e1\u00e6\u00ec\u00ef"+
		"\u00f4\u00fe\u010c\u010e\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}