import com.aliyuncs.exceptions.ClientException;
import com.itheima.utils.SMSUtils;

/**
 * @PackageName: PACKAGE_NAME
 * @ClassName: Demo
 * @Author: QingFeng
 * @Date: 2019-12-6 22:27
 * @Description: //TODO
 */

public class Demo {
    public static void main(String[] args) throws ClientException {
        SMSUtils.sendShortMessage("SMS_179612410","18740309892","5210");
    }

}
