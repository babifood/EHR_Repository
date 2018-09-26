package com.babifood.utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Base64加解密算法
 * </p>
 * Base64加密算法：<br/>
 * 1、获取字符串中每个字符的ASCII码；<br/>
 * 2、按照每3个8bit的字符为一组来分组，即每组24bit；<br/>
 * 3、将这24bit划分成4个6bit的4个单位，每个单位前面添加2个0，得到4个8bit的单位；<br/>
 * 4、将每个8bit的单位转换成十进制数字，对照Base64编码表找到对应的字符进行拼接，得到最终的加密后的字符串。<br/>
 * </p>
 * Base64解密算法：<br/>
 * 1、读入4个字符，对照Base64编码表找到字符对应的索引，生成4个6为的值；<br/>
 * 2、将这4个6为的值拼接起来，形成一个24为的值；<br/>
 * 3、将这个24位的值按照8位一组截断成3个8位的值；<br/>
 * 4、对照ASCII表找到这三个8位的值对应的字符，即解码后的字符。<br/>
 * </p>
 * 注意事项：<br/>
 * 1、被编码的字符必须是8bit的，即必须在ASCII码范围内，中文不行；<br/>
 * 2、如果被编码的字符长度不是3的倍数，则在最后添加1或2个0，对应的输出字符为“=”；
 * 3、给定一个字符串，用Base64方法对其进行加密后解密，得到的结果就不是开始时候的字符串了。<br/>
 */
public class BASE64Util {
	
	private static DecimalFormat numberFormat = new DecimalFormat("#0.00");
	
	 /**
     * 功能：编码字符串
     *
     * @date 2016年10月03日
     * @param data
     *            源字符串
     * @return String
     */
    public static String encode(String data) {
        return new String(encode(data.getBytes()));
    }
 
    /**
     * 功能：编码Double
     * @param data
     * @return
     */
    public static String encode(Double data) {
        return new String(encode(data.toString().getBytes()));
    }
    
    /**
     * 功能：解码字符串
     *
     * @date 2016年10月03日
     * @param data
     *            源字符串
     * @return String
     */
    public static String decode(String data) {
        return new String(decode(data.toCharArray()));
    }
 
    /**
     * 功能：编码byte[]
     *
     * @date 2016年10月03日
     * @param data
     *            源
     * @return char[]
     */
    public static char[] encode(byte[] data) {
        char[] out = new char[((data.length + 2) / 3) * 4];
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
            boolean quad = false;
            boolean trip = false;
 
            int val = (0xFF & (int) data[i]);
            val <<= 8;
            if ((i + 1) < data.length) {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < data.length) {
                val |= (0xFF & (int) data[i + 2]);
                quad = true;
            }
            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3F];
            val >>= 6;
            out[index + 0] = alphabet[val & 0x3F];
        }
        return out;
    }
 
    /**
     * 功能：解码
     *
     * @date 2016年10月03日
     * @param data
     *            编码后的字符数组
     * @return byte[]
     */
    public static byte[] decode(char[] data) {
 
        int tempLen = data.length;
        for (int ix = 0; ix < data.length; ix++) {
            if ((data[ix] > 255) || codes[data[ix]] < 0) {
                --tempLen; // ignore non-valid chars and padding
            }
        }
        // calculate required length:
        // -- 3 bytes for every 4 valid base64 chars
        // -- plus 2 bytes if there are 3 extra base64 chars,
        // or plus 1 byte if there are 2 extra.
 
        int len = (tempLen / 4) * 3;
        if ((tempLen % 4) == 3) {
            len += 2;
        }
        if ((tempLen % 4) == 2) {
            len += 1;
 
        }
        byte[] out = new byte[len];
 
        int shift = 0; // # of excess bits stored in accum
        int accum = 0; // excess bits
        int index = 0;
 
        // we now go through the entire array (NOT using the 'tempLen' value)
        for (int ix = 0; ix < data.length; ix++) {
            int value = (data[ix] > 255) ? -1 : codes[data[ix]];
 
            if (value >= 0) { // skip over non-code
                accum <<= 6; // bits shift up by 6 each time thru
                shift += 6; // loop, with new bits being put in
                accum |= value; // at the bottom.
                if (shift >= 8) { // whenever there are 8 or more shifted in,
                    shift -= 8; // write them out (from the top, leaving any
                    out[index++] = // excess at the bottom for next iteration.
                    (byte) ((accum >> shift) & 0xff);
                }
            }
        }
 
        // if there is STILL something wrong we just have to throw up now!
        if (index != out.length) {
            throw new Error("Miscalculated data length (wrote " + index
                    + " instead of " + out.length + ")");
        }
 
        return out;
    }
 
    /**
     * 功能：编码文件
     *
     * @date 2016年10月03日
     * @param file
     *            源文件
     */
    public static void encode(File file) throws IOException {
        if (!file.exists()) {
            System.exit(0);
        }
 
        else {
            byte[] decoded = readBytes(file);
            char[] encoded = encode(decoded);
            writeChars(file, encoded);
        }
        file = null;
    }
 
    /**
     * 功能：解码文件。
     *
     * @date 2016年10月03日
     * @param file
     *            源文件
     * @throws IOException
     */
    public static void decode(File file) throws IOException {
        if (!file.exists()) {
            System.exit(0);
        } else {
            char[] encoded = readChars(file);
            byte[] decoded = decode(encoded);
            writeBytes(file, decoded);
        }
        file = null;
    }
 
    //
    // code characters for values 0..63
    //
    private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
            .toCharArray();
 
    //
    // lookup table for converting base64 characters to value in range 0..63
    //
    private static byte[] codes = new byte[256];
    static {
        for (int i = 0; i < 256; i++) {
            codes[i] = -1;
            // LoggerUtil.debug(i + "&" + codes[i] + " ");
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            codes[i] = (byte) (i - 'A');
            // LoggerUtil.debug(i + "&" + codes[i] + " ");
        }
 
        for (int i = 'a'; i <= 'z'; i++) {
            codes[i] = (byte) (26 + i - 'a');
            // LoggerUtil.debug(i + "&" + codes[i] + " ");
        }
        for (int i = '0'; i <= '9'; i++) {
            codes[i] = (byte) (52 + i - '0');
            // LoggerUtil.debug(i + "&" + codes[i] + " ");
        }
        codes['+'] = 62;
        codes['/'] = 63;
    }
 
    private static byte[] readBytes(File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = null;
        InputStream fis = null;
        InputStream is = null;
        try {
            fis = new FileInputStream(file);
            is = new BufferedInputStream(fis);
            int count = 0;
            byte[] buf = new byte[16384];
            while ((count = is.read(buf)) != -1) {
                if (count > 0) {
                    baos.write(buf, 0, count);
                }
            }
            b = baos.toByteArray();
 
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (is != null)
                    is.close();
                if (baos != null)
                    baos.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
 
        return b;
    }
 
    private static char[] readChars(File file) throws IOException {
        CharArrayWriter caw = new CharArrayWriter();
        Reader fr = null;
        Reader in = null;
        try {
            fr = new FileReader(file);
            in = new BufferedReader(fr);
            int count = 0;
            char[] buf = new char[16384];
            while ((count = in.read(buf)) != -1) {
                if (count > 0) {
                    caw.write(buf, 0, count);
                }
            }
 
        } finally {
            try {
                if (caw != null)
                    caw.close();
                if (in != null)
                    in.close();
                if (fr != null)
                    fr.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
 
        return caw.toCharArray();
    }
 
    private static void writeBytes(File file, byte[] data) throws IOException {
        OutputStream fos = null;
        OutputStream os = null;
        try {
            fos = new FileOutputStream(file);
            os = new BufferedOutputStream(fos);
            os.write(data);
 
        } finally {
            try {
                if (os != null)
                    os.close();
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
 
    private static void writeChars(File file, char[] data) throws IOException {
        Writer fos = null;
        Writer os = null;
        try {
            fos = new FileWriter(file);
            os = new BufferedWriter(fos);
            os.write(data);
 
        } finally {
            try {
                if (os != null)
                    os.close();
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * @param baseSalaryList
     */
    public static void Base64DecodeMap(List<Map<String, Object>> baseSalaryList){
		if(baseSalaryList == null || baseSalaryList.size() <= 0){
			return;
		}
		for(Map<String, Object> map : baseSalaryList){
			if(!UtilString.isEmpty(map.get("baseSalary")+"")){
				map.put("baseSalary", BASE64Util.decode(map.get("baseSalary")+""));
			}
			if(!UtilString.isEmpty(map.get("fixedOverTimeSalary")+"")){
				map.put("fixedOverTimeSalary", BASE64Util.decode(map.get("fixedOverTimeSalary")+""));
			}
			if(!UtilString.isEmpty(map.get("companySalary")+"")){
				map.put("companySalary", BASE64Util.decode(map.get("companySalary")+""));
			}
			if(!UtilString.isEmpty(map.get("postSalary")+"")){
				map.put("postSalary", BASE64Util.decode(map.get("postSalary")+""));
			}
			if(!UtilString.isEmpty(map.get("callSubsidies")+"")){
				map.put("callSubsidies", BASE64Util.decode(map.get("callSubsidies")+""));
			}
			if(!UtilString.isEmpty(map.get("performanceSalary")+"")){
				map.put("performanceSalary", BASE64Util.decode(map.get("performanceSalary")+""));
			}
			if(!UtilString.isEmpty(map.get("singelMeal")+"")){
				map.put("singelMeal", BASE64Util.decode(map.get("singelMeal")+""));
			}
			if(!UtilString.isEmpty(map.get("overSalary")+"")){
				map.put("overSalary", BASE64Util.decode(map.get("overSalary")+""));
			}
			if(!UtilString.isEmpty(map.get("riceStick")+"")){
				map.put("riceStick", BASE64Util.decode(map.get("riceStick")+""));
			}
			if(!UtilString.isEmpty(map.get("compensatory")+"")){
				map.put("compensatory", BASE64Util.decode(map.get("compensatory")+""));
			}
			if(!UtilString.isEmpty(map.get("highTem")+"")){
				map.put("highTem", BASE64Util.decode(map.get("highTem")+""));
			}
			if(!UtilString.isEmpty(map.get("lowTem")+"")){
				map.put("lowTem", BASE64Util.decode(map.get("lowTem")+""));
			}
			if(!UtilString.isEmpty(map.get("morningShift")+"")){
				map.put("morningShift", BASE64Util.decode(map.get("morningShift")+""));
			}
			if(!UtilString.isEmpty(map.get("nightShift")+"")){
				map.put("nightShift", BASE64Util.decode(map.get("nightShift")+""));
			}
			if(!UtilString.isEmpty(map.get("otherAllowance")+"")){
				map.put("otherAllowance", BASE64Util.decode(map.get("otherAllowance")+""));
			}
			if(!UtilString.isEmpty(map.get("stay")+"")){
				map.put("stay", BASE64Util.decode(map.get("stay")+""));
			}
			if(!UtilString.isEmpty(map.get("performanceBonus")+"")){
				map.put("performanceBonus", BASE64Util.decode(map.get("performanceBonus")+""));
			}
			if(!UtilString.isEmpty(map.get("security")+"")){
				map.put("security", BASE64Util.decode(map.get("security")+""));
			}
			if(!UtilString.isEmpty(map.get("otherBonus")+"")){
				map.put("otherBonus", BASE64Util.decode(map.get("otherBonus")+""));
			}
			if(!UtilString.isEmpty(map.get("beforeDeduction")+"")){
				map.put("beforeDeduction", BASE64Util.decode(map.get("beforeDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("afterOtherDeduction")+"")){
				map.put("afterOtherDeduction", BASE64Util.decode(map.get("afterOtherDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("addOther")+"")){
				map.put("addOther", BASE64Util.decode(map.get("addOther")+""));
			}
			if(!UtilString.isEmpty(map.get("mealDeduction")+"")){
				map.put("mealDeduction", BASE64Util.decode(map.get("mealDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("dormBonus")+"")){
				map.put("dormBonus", BASE64Util.decode(map.get("dormBonus")+""));
			}
			if(!UtilString.isEmpty(map.get("dormDeduction")+"")){
				map.put("dormDeduction", BASE64Util.decode(map.get("dormDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("dormFee")+"")){
				map.put("dormFee", BASE64Util.decode(map.get("dormFee")+""));
			}
			if(!UtilString.isEmpty(map.get("electricityFee")+"")){
				map.put("electricityFee", BASE64Util.decode(map.get("electricityFee")+""));
			}
			if(!UtilString.isEmpty(map.get("insurance")+"")){
				map.put("insurance", BASE64Util.decode(map.get("insurance")+""));
			}
			if(!UtilString.isEmpty(map.get("providentFund")+"")){
				map.put("providentFund", BASE64Util.decode(map.get("providentFund")+""));
			}
			if(!UtilString.isEmpty(map.get("afterDeduction")+"")){
				map.put("afterDeduction", BASE64Util.decode(map.get("afterDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("yearDeduction")+"")){
				map.put("yearDeduction", BASE64Util.decode(map.get("yearDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("relaxation")+"")){
				map.put("relaxation", BASE64Util.decode(map.get("relaxation")+""));
			}
			if(!UtilString.isEmpty(map.get("sickDeduction")+"")){
				map.put("sickDeduction", BASE64Util.decode(map.get("sickDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("trainDeduction")+"")){
				map.put("trainDeduction", BASE64Util.decode(map.get("trainDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("parentalDeduction")+"")){
				map.put("parentalDeduction", BASE64Util.decode(map.get("parentalDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("marriageDeduction")+"")){
				map.put("marriageDeduction", BASE64Util.decode(map.get("marriageDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("companionParentalDeduction")+"")){
				map.put("companionParentalDeduction", BASE64Util.decode(map.get("companionParentalDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("funeralDeduction")+"")){
				map.put("funeralDeduction", BASE64Util.decode(map.get("funeralDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("totalDeduction")+"")){
				map.put("totalDeduction", BASE64Util.decode(map.get("totalDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("wagePayable")+"")){
				map.put("wagePayable", BASE64Util.decode(map.get("wagePayable")+""));
			}
			if(!UtilString.isEmpty(map.get("personalTax")+"")){
				map.put("personalTax", BASE64Util.decode(map.get("personalTax")+""));
			}
			if(!UtilString.isEmpty(map.get("realWages")+"")){
				map.put("realWages", BASE64Util.decode(map.get("realWages")+""));
			}
			if(!UtilString.isEmpty(map.get("thingDeduction")+"")){
				map.put("thingDeduction", BASE64Util.decode(map.get("thingDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("onboarding")+"")){
				map.put("onboarding", BASE64Util.decode(map.get("onboarding")+""));
			}
			if(!UtilString.isEmpty(map.get("laterAndLeaveDeduction")+"")){
				map.put("laterAndLeaveDeduction", BASE64Util.decode(map.get("laterAndLeaveDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("completionDeduction")+"")){
				map.put("completionDeduction", BASE64Util.decode(map.get("completionDeduction")+""));
			}
			if(!UtilString.isEmpty(map.get("pSalary")+"")){
				map.put("pSalary", BASE64Util.decode(map.get("pSalary")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved1")+"")){
				map.put("reserved1", BASE64Util.decode(map.get("reserved1")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved2")+"")){
				map.put("reserved2", BASE64Util.decode(map.get("reserved2")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved3")+"")){
				map.put("reserved3", BASE64Util.decode(map.get("reserved3")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved4")+"")){
				map.put("reserved4", BASE64Util.decode(map.get("reserved4")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved5")+"")){
				map.put("reserved5", BASE64Util.decode(map.get("reserved5")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved6")+"")){
				map.put("reserved6", BASE64Util.decode(map.get("reserved6")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved7")+"")){
				map.put("reserved7", BASE64Util.decode(map.get("reserved7")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved8")+"")){
				map.put("reserved8", BASE64Util.decode(map.get("reserved8")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved9")+"")){
				map.put("reserved9", BASE64Util.decode(map.get("reserved9")+""));
			}
			if(!UtilString.isEmpty(map.get("reserved10")+"")){
				map.put("reserved10", BASE64Util.decode(map.get("reserved10")+""));
			}
		}
	}
    
    public static String getDecodeStringTowDecimal(Object obj) {
    	String decodeStr = "";
		try {
			decodeStr = encode(getStringTowDecimal(obj));
		} catch (Exception e) {
			decodeStr = (String) obj;
		}
		return decodeStr;
	}
    
    public static String getStringTowDecimal(Object obj) {
    	String decodeStr = "";
		try {
			decodeStr = UtilString.isEmpty(obj+"") ? "0" : obj.toString();
			decodeStr = numberFormat.format(Double.valueOf(decodeStr));
		} catch (Exception e) {
			decodeStr = (String) obj;
		}
		return decodeStr;
	}
    
    public static void main(String[] args) {
    	System.out.println(getDecodeStringTowDecimal("123456"));
    	System.out.println(decode(getDecodeStringTowDecimal("123456")));
//		String salary = "10000";
//		String code = encode(salary);
//		System.out.println(code);
//		System.out.println(decode(code));
	}
}
