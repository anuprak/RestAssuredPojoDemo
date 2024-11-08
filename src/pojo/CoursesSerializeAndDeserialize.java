package pojo;

import java.util.List;

public class CoursesSerializeAndDeserialize {

	private List<WebAutomationCoursesSerializeAndDeserialize> webAutomation;
	private List<ApiCoursesSerializeAndDeserialize> api;
	private List<MobileCoursesSerializeAndDeserialize> mobile;

	public List<WebAutomationCoursesSerializeAndDeserialize> getWebAutomation() {
		return webAutomation;
	}

	public void setWebAutomation(List<WebAutomationCoursesSerializeAndDeserialize> webAutomation) {
		this.webAutomation = webAutomation;
	}

	public List<ApiCoursesSerializeAndDeserialize> getApi() {
		return api;
	}

	public void setApi(List<ApiCoursesSerializeAndDeserialize> api) {
		this.api = api;
	}

	public List<MobileCoursesSerializeAndDeserialize> getMobile() {
		return mobile;
	}

	public void setMobile(List<MobileCoursesSerializeAndDeserialize> mobile) {
		this.mobile = mobile;
	}

}
