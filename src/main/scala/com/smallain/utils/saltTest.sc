
import java.lang._

import com.smallain.utils.Md5Utils
import org.apache.hadoop.hbase.util.Bytes
val longLength = Long.SIZE / 8
val userHash = Md5Utils.md5sum("wuyu")

val timestamp = Bytes.toBytes(-1 * 1329088818321L)




def md5HashString(s: String): String = {
  import java.security.MessageDigest
  import java.math.BigInteger
  val md = MessageDigest.getInstance("MD5")
  val digest = md.digest(s.getBytes)
  val bigInt = new BigInteger(1,digest)
  val hashedString = bigInt.toString(16)
  hashedString
}
val wuyuhang = md5HashString("1329088818321")

val salt = new Integer(millisecond.hashCode()).shortValue() % 46
//5a38967c8abb3f2a4e281f58c660c8bf
//5a38967c8abb3f2a4e281f58c660c8bf
//5a38967c8abb3f2a4e281f58c660c8bf
//dab9f46af620110ca460128336a5d07a
//dab9f46af620110ca460128336a5d07a
//54c488247e73a393161d5f8bbd612b73
//54c488247e73a393161d5f8bbd612b73