package jazari.utils;

// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import io.kaitai.struct.KaitaiStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.nio.charset.Charset;


/**
 * Python Pickle format serializes Python objects to a byte stream, as a sequence
 * of operations to run on the Pickle Virtual Machine.
 * 
 * The format is mostly implementation defined, there is no formal specification.
 * Pickle data types are closely coupled to the Python object model.
 * Python singletons, and most builtin types (e.g. `None`, `int`,`dict`, `list`)
 * are serialised using dedicated Pickle opcodes.
 * Other builtin types, and all classes  (e.g. `set`, `datetime.datetime`) are
 * serialised by encoding the name of a constructor callable.
 * They are deserialised by importing that constructor, and calling it.
 * So, unpickling an arbitrary pickle, using the Python's stdlib pickle module
 * can cause arbitrary code execution.
 * 
 * Pickle format has evolved with Python, later protocols add opcodes & types.
 * Later Python releases can pickle to or unpickle from any earlier protocol.
 * 
 * * Protocol 0: ASCII clean, no explicit version, fields are '\n' terminated.
 * * Protocol 1: Binary, no explicit version, first length prefixed types.
 * * Protocol 2: Python 2.3+. Explicit versioning, more length prefixed types.
 *   https://www.python.org/dev/peps/pep-0307/
 * * Protocol 3: Python 3.0+. Dedicated opcodes for `bytes` objects.
 * * Protocol 4: Python 3.4+. Opcodes for 64 bit strings, framing, `set`.
 *   https://www.python.org/dev/peps/pep-3154/
 * * Protocol 5: Python 3.8+: Opcodes for `bytearray` and out of band data
 *   https://www.python.org/dev/peps/pep-0574/
 * @see <a href="https://github.com/python/cpython/blob/v3.8.1/Lib/pickletools.py">Source</a>
 */
public class PythonPickle extends KaitaiStruct {
    public static PythonPickle fromFile(String fileName) throws IOException {
        return new PythonPickle(new ByteBufferKaitaiStream(fileName));
    }

    public enum Opcode {
        MARK(40),
        EMPTY_TUPLE(41),
        STOP(46),
        POP(48),
        POP_MARK(49),
        DUP(50),
        BINBYTES(66),
        SHORT_BINBYTES(67),
        FLOAT(70),
        BINFLOAT(71),
        INT(73),
        BININT(74),
        BININT1(75),
        LONG(76),
        BININT2(77),
        NONE(78),
        PERSID(80),
        BINPERSID(81),
        REDUCE(82),
        STRING(83),
        BINSTRING(84),
        SHORT_BINSTRING(85),
        UNICODE(86),
        BINUNICODE(88),
        EMPTY_LIST(93),
        APPEND(97),
        BUILD(98),
        GLOBAL_OPCODE(99),
        DICT(100),
        APPENDS(101),
        GET(103),
        BINGET(104),
        INST(105),
        LONG_BINGET(106),
        LIST(108),
        OBJ(111),
        PUT(112),
        BINPUT(113),
        LONG_BINPUT(114),
        SETITEM(115),
        TUPLE(116),
        SETITEMS(117),
        EMPTY_DICT(125),
        PROTO(128),
        NEWOBJ(129),
        EXT1(130),
        EXT2(131),
        EXT4(132),
        TUPLE1(133),
        TUPLE2(134),
        TUPLE3(135),
        NEWTRUE(136),
        NEWFALSE(137),
        LONG1(138),
        LONG4(139),
        SHORT_BINUNICODE(140),
        BINUNICODE8(141),
        BINBYTES8(142),
        EMPTY_SET(143),
        ADDITEMS(144),
        FROZENSET(145),
        NEWOBJ_EX(146),
        STACK_GLOBAL(147),
        MEMOIZE(148),
        FRAME(149),
        BYTEARRAY8(150),
        NEXT_BUFFER(151),
        READONLY_BUFFER(152);

        private final long id;
        Opcode(long id) { this.id = id; }
        public long id() { return id; }
        private static final Map<Long, Opcode> byId = new HashMap<Long, Opcode>(68);
        static {
            for (Opcode e : Opcode.values())
                byId.put(e.id(), e);
        }
        public static Opcode byId(long id) { return byId.get(id); }
    }

    public PythonPickle(KaitaiStream _io) {
        this(_io, null, null);
    }

    public PythonPickle(KaitaiStream _io, KaitaiStruct _parent) {
        this(_io, _parent, null);
    }

    public PythonPickle(KaitaiStream _io, KaitaiStruct _parent, PythonPickle _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root == null ? this : _root;
        _read();
    }
    private void _read() {
        this.ops = new ArrayList<Op>();
        {
            Op _it;
            int i = 0;
            do {
                _it = new Op(this._io, this, _root);
                this.ops.add(_it);
                i++;
            } while (!(_it.code() == Opcode.STOP));
        }
    }

    /**
     * Length prefixed string, between 0 and 2**64-1 bytes long.
     * 
     * Only a 64-bit build of Python would produce a pickle containing strings
     * large enough to need this type. Such a pickle could not be unpickled on
     * a 32-bit build of Python, because the string would be larger than
     * `sys.maxsize`.
     */
    public static class Unicodestring8 extends KaitaiStruct {
        public static Unicodestring8 fromFile(String fileName) throws IOException {
            return new Unicodestring8(new ByteBufferKaitaiStream(fileName));
        }

        public Unicodestring8(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Unicodestring8(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Unicodestring8(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU8le();
            this.val = new String(this._io.readBytes(len()), Charset.forName("utf8"));
        }
        private long len;
        private String val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public long len() { return len; }
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Large signed integer, in the range -2**(8*255-1) to 2**(8*255-1)-1,
     * encoded as two's complement.
     */
    public static class Long1 extends KaitaiStruct {
        public static Long1 fromFile(String fileName) throws IOException {
            return new Long1(new ByteBufferKaitaiStream(fileName));
        }

        public Long1(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Long1(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Long1(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU1();
            this.val = this._io.readBytes(len());
        }
        private int len;
        private byte[] val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public int len() { return len; }
        public byte[] val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Length prefixed string, between 0 and 2**64-1 bytes long.
     * 
     * Only a 64-bit build of Python would produce a pickle containing strings
     * large enough to need this type. Such a pickle could not be unpickled on
     * a 32-bit build of Python, because the string would be larger than
     * `sys.maxsize`.
     */
    public static class Bytes8 extends KaitaiStruct {
        public static Bytes8 fromFile(String fileName) throws IOException {
            return new Bytes8(new ByteBufferKaitaiStream(fileName));
        }

        public Bytes8(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Bytes8(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Bytes8(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU8le();
            this.val = this._io.readBytes(len());
        }
        private long len;
        private byte[] val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public long len() { return len; }
        public byte[] val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Length prefixed byte string, between 0 and 255 bytes long.
     */
    public static class Bytes1 extends KaitaiStruct {
        public static Bytes1 fromFile(String fileName) throws IOException {
            return new Bytes1(new ByteBufferKaitaiStream(fileName));
        }

        public Bytes1(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Bytes1(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Bytes1(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU1();
            this.val = this._io.readBytes(len());
        }
        private int len;
        private byte[] val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public int len() { return len; }
        public byte[] val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Length prefixed string, between 0 and 2**32-1 bytes long
     */
    public static class Bytes4 extends KaitaiStruct {
        public static Bytes4 fromFile(String fileName) throws IOException {
            return new Bytes4(new ByteBufferKaitaiStream(fileName));
        }

        public Bytes4(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Bytes4(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Bytes4(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU4le();
            this.val = this._io.readBytes(len());
        }
        private long len;
        private byte[] val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public long len() { return len; }
        public byte[] val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Some opcodes take no argument, this empty type is used for them.
     */
    public static class NoArg extends KaitaiStruct {
        public static NoArg fromFile(String fileName) throws IOException {
            return new NoArg(new ByteBufferKaitaiStream(fileName));
        }

        public NoArg(KaitaiStream _io) {
            this(_io, null, null);
        }

        public NoArg(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public NoArg(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
        }
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Unquoted string, does not contain string escapes.
     */
    public static class StringnlNoescape extends KaitaiStruct {
        public static StringnlNoescape fromFile(String fileName) throws IOException {
            return new StringnlNoescape(new ByteBufferKaitaiStream(fileName));
        }

        public StringnlNoescape(KaitaiStream _io) {
            this(_io, null, null);
        }

        public StringnlNoescape(KaitaiStream _io, KaitaiStruct _parent) {
            this(_io, _parent, null);
        }

        public StringnlNoescape(KaitaiStream _io, KaitaiStruct _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.val = new String(this._io.readBytesTerm((byte) 10, false, true, true), Charset.forName("ascii"));
        }
        private String val;
        private PythonPickle _root;
        private KaitaiStruct _parent;
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public KaitaiStruct _parent() { return _parent; }
    }

    /**
     * Integer, encoded with the ASCII chracters [0-9-], followed by 'L'.
     */
    public static class DecimalnlLong extends KaitaiStruct {
        public static DecimalnlLong fromFile(String fileName) throws IOException {
            return new DecimalnlLong(new ByteBufferKaitaiStream(fileName));
        }

        public DecimalnlLong(KaitaiStream _io) {
            this(_io, null, null);
        }

        public DecimalnlLong(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public DecimalnlLong(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.val = new String(this._io.readBytesTerm((byte) 10, false, true, true), Charset.forName("ascii"));
        }
        private String val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Length prefixed string, between 0 and 2**32-1 bytes long
     */
    public static class Unicodestring4 extends KaitaiStruct {
        public static Unicodestring4 fromFile(String fileName) throws IOException {
            return new Unicodestring4(new ByteBufferKaitaiStream(fileName));
        }

        public Unicodestring4(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Unicodestring4(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Unicodestring4(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU4le();
            this.val = new String(this._io.readBytes(len()), Charset.forName("utf8"));
        }
        private long len;
        private String val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public long len() { return len; }
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Unquoted string, containing Python Unicode escapes.
     */
    public static class Unicodestringnl extends KaitaiStruct {
        public static Unicodestringnl fromFile(String fileName) throws IOException {
            return new Unicodestringnl(new ByteBufferKaitaiStream(fileName));
        }

        public Unicodestringnl(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Unicodestringnl(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Unicodestringnl(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.val = new String(this._io.readBytesTerm((byte) 10, false, true, true), Charset.forName("ascii"));
        }
        private String val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Large signed integer, in the range -2**(8*2**32-1) to 2**(8*2**32-1)-1,
     * encoded as two's complement.
     */
    public static class Long4 extends KaitaiStruct {
        public static Long4 fromFile(String fileName) throws IOException {
            return new Long4(new ByteBufferKaitaiStream(fileName));
        }

        public Long4(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Long4(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Long4(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU4le();
            this.val = this._io.readBytes(len());
        }
        private long len;
        private byte[] val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public long len() { return len; }
        public byte[] val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Length prefixed string, between 0 and 255 bytes long. Encoding is
     * unspecified.
     * 
     * The default Python 2.x string type (`str`) is a sequence of bytes.
     * These are pickled as `string1` or `string4`, when protocol == 2.
     * The bytes are written directly, no explicit encoding is performed.
     * 
     * Python 3.x will not pickle an object as `string1` or `string4`.
     * Instead, opcodes and types with a known encoding are used.
     * When unpickling
     * 
     * - `pickle.Unpickler` objects default to ASCII, which can be overriden
     * - `pickletools.dis` uses latin1, and cannot be overriden
     * @see <a href="https://github.com/python/cpython/blob/bb8071a4/Lib/pickle.py#L486-L495">Source</a>
     */
    public static class String1 extends KaitaiStruct {
        public static String1 fromFile(String fileName) throws IOException {
            return new String1(new ByteBufferKaitaiStream(fileName));
        }

        public String1(KaitaiStream _io) {
            this(_io, null, null);
        }

        public String1(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public String1(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU1();
            this.val = this._io.readBytes(len());
        }
        private int len;
        private byte[] val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public int len() { return len; }
        public byte[] val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Length prefixed string, between 0 and 2**64-1 bytes long.
     * 
     * The contents are deserilised into a `bytearray` object.
     */
    public static class Bytearray8 extends KaitaiStruct {
        public static Bytearray8 fromFile(String fileName) throws IOException {
            return new Bytearray8(new ByteBufferKaitaiStream(fileName));
        }

        public Bytearray8(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Bytearray8(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Bytearray8(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU8le();
            this.val = this._io.readBytes(len());
        }
        private long len;
        private byte[] val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public long len() { return len; }
        public byte[] val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Integer or boolean, encoded with the ASCII characters [0-9-].
     * 
     * The values '00' and '01' encode the Python values `False` and `True`.
     * Normally a value would not contain leading '0' characters.
     */
    public static class DecimalnlShort extends KaitaiStruct {
        public static DecimalnlShort fromFile(String fileName) throws IOException {
            return new DecimalnlShort(new ByteBufferKaitaiStream(fileName));
        }

        public DecimalnlShort(KaitaiStream _io) {
            this(_io, null, null);
        }

        public DecimalnlShort(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public DecimalnlShort(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.val = new String(this._io.readBytesTerm((byte) 10, false, true, true), Charset.forName("ascii"));
        }
        private String val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Length prefixed string, between 0 and 255 bytes long
     */
    public static class Unicodestring1 extends KaitaiStruct {
        public static Unicodestring1 fromFile(String fileName) throws IOException {
            return new Unicodestring1(new ByteBufferKaitaiStream(fileName));
        }

        public Unicodestring1(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Unicodestring1(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Unicodestring1(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readU1();
            this.val = new String(this._io.readBytes(len()), Charset.forName("utf8"));
        }
        private int len;
        private String val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public int len() { return len; }
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Quoted string, possibly containing Python string escapes.
     */
    public static class Stringnl extends KaitaiStruct {
        public static Stringnl fromFile(String fileName) throws IOException {
            return new Stringnl(new ByteBufferKaitaiStream(fileName));
        }

        public Stringnl(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Stringnl(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Stringnl(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.val = new String(this._io.readBytesTerm((byte) 10, false, true, true), Charset.forName("ascii"));
        }
        private String val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Pair of unquoted, unescaped strings.
     */
    public static class StringnlNoescapePair extends KaitaiStruct {
        public static StringnlNoescapePair fromFile(String fileName) throws IOException {
            return new StringnlNoescapePair(new ByteBufferKaitaiStream(fileName));
        }

        public StringnlNoescapePair(KaitaiStream _io) {
            this(_io, null, null);
        }

        public StringnlNoescapePair(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public StringnlNoescapePair(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.val1 = new StringnlNoescape(this._io, this, _root);
            this.val2 = new StringnlNoescape(this._io, this, _root);
        }
        private StringnlNoescape val1;
        private StringnlNoescape val2;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public StringnlNoescape val1() { return val1; }
        public StringnlNoescape val2() { return val2; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }

    /**
     * Length prefixed string, between 0 and 2**31-1 bytes long. Encoding is
     * unspecified.
     * 
     * Although the len field is signed, any length < 0 will raise an exception
     * during unpickling.
     * 
     * See the documentation for `string1` for further detail about encodings.
     * @see <a href="https://github.com/python/cpython/blob/bb8071a4/Lib/pickle.py#L486-L495">Source</a>
     */
    public static class String4 extends KaitaiStruct {
        public static String4 fromFile(String fileName) throws IOException {
            return new String4(new ByteBufferKaitaiStream(fileName));
        }

        public String4(KaitaiStream _io) {
            this(_io, null, null);
        }

        public String4(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public String4(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.len = this._io.readS4le();
            this.val = this._io.readBytes(len());
        }
        private int len;
        private byte[] val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public int len() { return len; }
        public byte[] val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }
    public static class Op extends KaitaiStruct {
        public static Op fromFile(String fileName) throws IOException {
            return new Op(new ByteBufferKaitaiStream(fileName));
        }

        public Op(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Op(KaitaiStream _io, PythonPickle _parent) {
            this(_io, _parent, null);
        }

        public Op(KaitaiStream _io, PythonPickle _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.code = PythonPickle.Opcode.byId(this._io.readU1());
            {
                Opcode on = code();
                if (on != null) {
                    switch (code()) {
                    case EXT4: {
                        this.arg = (Object) (this._io.readU4le());
                        break;
                    }
                    case TUPLE1: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case SETITEM: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case READONLY_BUFFER: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case STOP: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case EXT2: {
                        this.arg = (Object) (this._io.readU2le());
                        break;
                    }
                    case EMPTY_TUPLE: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case NEWTRUE: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case LONG: {
                        this.arg = new DecimalnlLong(this._io, this, _root);
                        break;
                    }
                    case NEWOBJ: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case BYTEARRAY8: {
                        this.arg = new Bytearray8(this._io, this, _root);
                        break;
                    }
                    case PUT: {
                        this.arg = new DecimalnlShort(this._io, this, _root);
                        break;
                    }
                    case STACK_GLOBAL: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case POP_MARK: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case APPEND: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case NEWFALSE: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case BINPERSID: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case BUILD: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case EMPTY_DICT: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case TUPLE2: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case LONG4: {
                        this.arg = new Long4(this._io, this, _root);
                        break;
                    }
                    case NEXT_BUFFER: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case APPENDS: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case BINBYTES: {
                        this.arg = new Bytes4(this._io, this, _root);
                        break;
                    }
                    case DUP: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case LIST: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case PROTO: {
                        this.arg = (Object) (this._io.readU1());
                        break;
                    }
                    case POP: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case FRAME: {
                        this.arg = (Object) (this._io.readU8le());
                        break;
                    }
                    case STRING: {
                        this.arg = new Stringnl(this._io, this, _root);
                        break;
                    }
                    case BINUNICODE: {
                        this.arg = new Unicodestring4(this._io, this, _root);
                        break;
                    }
                    case FLOAT: {
                        this.arg = new Floatnl(this._io, this, _root);
                        break;
                    }
                    case REDUCE: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case GLOBAL_OPCODE: {
                        this.arg = new StringnlNoescapePair(this._io, this, _root);
                        break;
                    }
                    case BINPUT: {
                        this.arg = (Object) (this._io.readU1());
                        break;
                    }
                    case MEMOIZE: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case PERSID: {
                        this.arg = new StringnlNoescape(this._io, this, _root);
                        break;
                    }
                    case EXT1: {
                        this.arg = (Object) (this._io.readU1());
                        break;
                    }
                    case NONE: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case SHORT_BINUNICODE: {
                        this.arg = new Unicodestring1(this._io, this, _root);
                        break;
                    }
                    case OBJ: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case BINFLOAT: {
                        this.arg = (Object) (this._io.readF8be());
                        break;
                    }
                    case NEWOBJ_EX: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case EMPTY_LIST: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case TUPLE: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case BINUNICODE8: {
                        this.arg = new Unicodestring8(this._io, this, _root);
                        break;
                    }
                    case BINGET: {
                        this.arg = (Object) (this._io.readU1());
                        break;
                    }
                    case DICT: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case BINSTRING: {
                        this.arg = new String4(this._io, this, _root);
                        break;
                    }
                    case SETITEMS: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case BININT2: {
                        this.arg = (Object) (this._io.readU2le());
                        break;
                    }
                    case BINBYTES8: {
                        this.arg = new Bytes8(this._io, this, _root);
                        break;
                    }
                    case BININT1: {
                        this.arg = (Object) (this._io.readU1());
                        break;
                    }
                    case INST: {
                        this.arg = new StringnlNoescapePair(this._io, this, _root);
                        break;
                    }
                    case LONG_BINGET: {
                        this.arg = (Object) (this._io.readU4le());
                        break;
                    }
                    case LONG_BINPUT: {
                        this.arg = (Object) (this._io.readU4le());
                        break;
                    }
                    case INT: {
                        this.arg = new DecimalnlShort(this._io, this, _root);
                        break;
                    }
                    case BININT: {
                        this.arg = (Object) (this._io.readS4le());
                        break;
                    }
                    case UNICODE: {
                        this.arg = new Unicodestringnl(this._io, this, _root);
                        break;
                    }
                    case LONG1: {
                        this.arg = new Long1(this._io, this, _root);
                        break;
                    }
                    case SHORT_BINSTRING: {
                        this.arg = new String1(this._io, this, _root);
                        break;
                    }
                    case MARK: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case FROZENSET: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case TUPLE3: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case ADDITEMS: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case GET: {
                        this.arg = new DecimalnlShort(this._io, this, _root);
                        break;
                    }
                    case EMPTY_SET: {
                        this.arg = new NoArg(this._io, this, _root);
                        break;
                    }
                    case SHORT_BINBYTES: {
                        this.arg = new Bytes1(this._io, this, _root);
                        break;
                    }
                    }
                }
            }
        }
        private Opcode code;
        private Object arg;
        private PythonPickle _root;
        private PythonPickle _parent;

        /**
         * Operation code that determines which action should be
         * performed next by the Pickle Virtual Machine. Some opcodes
         * are only available in later versions of the Pickle protocol.
         */
        public Opcode code() { return code; }

        /**
         * Optional argument for the operation. Data type and length
         * are determined by the value of the opcode.
         */
        public Object arg() { return arg; }
        public PythonPickle _root() { return _root; }
        public PythonPickle _parent() { return _parent; }
    }

    /**
     * Double float, encoded with the ASCII characters [0-9.e+-], '-inf', 'inf',
     * or 'nan'.
     */
    public static class Floatnl extends KaitaiStruct {
        public static Floatnl fromFile(String fileName) throws IOException {
            return new Floatnl(new ByteBufferKaitaiStream(fileName));
        }

        public Floatnl(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Floatnl(KaitaiStream _io, PythonPickle.Op _parent) {
            this(_io, _parent, null);
        }

        public Floatnl(KaitaiStream _io, PythonPickle.Op _parent, PythonPickle _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.val = new String(this._io.readBytesTerm((byte) 10, false, true, true), Charset.forName("ascii"));
        }
        private String val;
        private PythonPickle _root;
        private PythonPickle.Op _parent;
        public String val() { return val; }
        public PythonPickle _root() { return _root; }
        public PythonPickle.Op _parent() { return _parent; }
    }
    private ArrayList<Op> ops;
    private PythonPickle _root;
    private KaitaiStruct _parent;
    public ArrayList<Op> ops() { return ops; }
    public PythonPickle _root() { return _root; }
    public KaitaiStruct _parent() { return _parent; }
}
