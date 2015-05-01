package jkt.centralisateur.common;

import java.util.HashMap;
import java.util.Map;

public class TypeMimeHelper {
	/** Liste des types mime et des extensions associées */
	private static final Map<String, String> types = new HashMap<String, String>() {
		/** Default serial version ID */
		private static final long serialVersionUID = 1L;
		
		/** Constructeur statique */
		{
			put("dwg", "application/acad");
			put("ccad", "application/clariscad");
			put("drw", "application/drafting");
			put("dxf", "application/dxf");
			put("unv", "application/i-deas");
			put("igs", "application/iges");
			put("iges", "application/iges");
			put("bin", "application/octet-stream");
			put("oda", "application/oda");
			put("pdf", "application/pdf");
			put("ai", "application/postscript");
			put("eps", "application/postscript");
			put("ps", "application/postscript");
			put("prt", "application/pro_eng");
			put("rtf", "application/rtf");
			put("set", "application/set");
			put("stl", "application/sla");
			put("dwg", "application/solids");
			put("step", "application/step");
			put("vda", "application/vda");
			put("mif", "application/x-mif");
			put("dwg", "application/x-csh");
			put("dvi", "application/x-dvi");
			put("hdf", "application/hdf");
			put("latex", "application/x-latex");
			put("nc", "application/x-netcdf");
			put("cdf", "application/x-netcdf");
			put("dwg", "application/x-sh");
			put("tcl", "application/x-tcl");
			put("tex", "application/x-tex");
			put("texinfo", "application/x-texinfo");
			put("texi", "application/x-texinfo");
			put("t", "application/x-troff");
			put("tr", "application/x-troff");
			put("troff", "application/x-troff");
			put("man", "application/x-troff-man");
			put("me", "application/x-troff-me");
			put("ms", "application/x-troff-ms");
			put("src", "application/x-wais-source");
			put("bcpio", "application/x-bcpio");
			put("cpio", "application/x-cpio");
			put("gtar", "application/x-gtar");
			put("shar", "application/x-shar");
			put("sv4cpio", "application/x-sv4cpio");
			put("sc4crc", "application/x-sv4crc");
			put("tar", "application/x-tar");
			put("man", "application/x-ustar");
			put("man", "application/zip");
			put("au", "audio/basic");
			put("snd", "audio/basic");
			put("aif", "audio/x-aiff");
			put("aiff", "audio/x-aiff");
			put("aifc", "audio/x-aiff");
			put("wav", "audio/x-wav");
			put("man", "image/gif");
			put("ief", "image/ief");
			put("jpg", "image/jpeg");
			put("jpeg", "image/jpeg");
			put("jpe", "image/jpeg");
			put("tiff", "image/tiff");
			put("tif", "image/tiff");
			put("cmu", "image/x-cmu-raster");
			put("pnm", "image/x-portable-anymap");
			put("pbm", "image/x-portable-bitmap");
			put("pgm", "image/x-portable-graymap");
			put("ppm", "image/x-portable-pixmap");
			put("rgb", "image/x-rgb");
			put("xbm", "image/x-xbitmap");
			put("xpm", "image/x-xpixmap");
			put("man", "image/x-xwindowdump");
			put("zip", "multipart/x-zip");
			put("gz", "multipart/x-gzip");
			put("gzip", "multipart/x-gzip");
			put("htm", "text/html");
			put("html", "text/html");
			put("txt", "text/plain");
			put("g", "text/plain");
			put("h", "text/plain");
			put("c", "text/plain");
			put("cc", "text/plain");
			put("hh", "text/plain");
			put("m", "text/plain");
			put("f90", "text/plain");
			put("rtx", "text/richtext");
			put("tsv", "text/tab-separated-value");
			put("etx", "text/x-setext");
			put("mpeg", "video/mpeg");
			put("mpg", "video/mpeg");
			put("mpe", "video/mpeg");
			put("qt", "video/quicktime");
			put("mov", "video/quicktime");
			put("avi", "video/msvideo");
			put("movie", "video/x-sgi-movie");
		}
	};
	
	/** Classe utilitaire => Masquage du constructeur */
	private TypeMimeHelper() {}
	
	/**
	 * Retourne le type Mime d'un fichier en fonction de son extension
	 * 
	 * @param extension extension du fichier
	 * @return type Mime
	 */
	public static String getTypeMimeFromExtention(final String extension) {
		return types.get(extension);
	}
}
