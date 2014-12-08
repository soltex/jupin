/**
 * 
 */


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.jupin.messagebox.Constants;
import com.vanstone.jupin.messagebox.Message;
import com.vanstone.jupin.messagebox.MessageBox;
import com.vanstone.jupin.messagebox.MessageBoxImpl;
import com.vanstone.jupin.messagebox.MessageHelper;

/**
 * @author shipeng
 *
 */
@ContextConfiguration(locations = { 
		"classpath*:/core-context.xml",
		"classpath*:META-INF/jupin**context-module.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageTest {
	
	@Autowired
	private ResourceBundleMessageSource resourceBundleMessageSource;
	
	@Test
	public void testMessageEntity() {
		for (int i=0;i<100000;i++) {
			Message message = MessageHelper.createNewMessage(i + ".Message Entity");
			message.send();
		}
	}
	
	@Test
	public void testReadMessageEntity() {
		MessageBox messageBox = new MessageBoxImpl(Constants.DEFAULT_MESSGEBOX_GROUP, Constants.DEFAULT_MESSGEBOX_NAME);
		Gson gson = GsonCreator.createPretty();
		for (int i=0;i<100000;i++) {
			Message message = messageBox.read();
			System.out.println(gson.toJson(message));
		}
	}
	
	@Test
	public void testi18n() {
		System.out.println(resourceBundleMessageSource.getMessage("aa", null, null));
		System.out.println(resourceBundleMessageSource.getMessage("vv", null, null));
	}
}
