package com.wi.templatemessageapi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wi.excelreadapi.entities.Details;
import com.wi.templatemessageapi.repository.TemplateMessageApiRepository;

//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;

import com.squareup.okhttp.*;

@Service
public class TemplateMessageApiServiceImpl implements TemplateMessageApiService {

	@Autowired
	TemplateMessageApiRepository templateMessageApiRepository;

	// Send Message Template to partucular user..
	// Accept (Details, String)
	@Override
	public String getTextTemplate(Details details, String template_id) {

		String staticTextTemplate = null;

		try {

			OkHttpClient client = new OkHttpClient(); // .newBuilder().build()

			MediaType JSON = MediaType.parse("application/json; charset=utf-8");

			boolean result = templateMessageApiRepository.existsById(template_id);

			List list = templateMessageApiRepository.getAllTemplateId();

			if (result) {
				// Static text Template
				if (list.get(0).toString().equals(template_id)) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + details.getNumber() + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"template\",\r\n" + "\"template\": {\r\n" + "\"body\": [],\r\n"
							+ "\"templateId\": \"" + template_id + "\",\r\n" + "\"langCode\": \"en\"\r\n" + "}\r\n"
							+ "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}";

					return staticTextTemplate;
				}
				// Dynamic text Template
				else if (list.get(1).toString().equals(template_id)) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + details.getNumber() + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"template\",\r\n" + "\"template\": {\r\n" + "\"body\": [\r\n" + "{\r\n"
							+ "\"type\": \"text\",\r\n" + "\"text\": \"" + details.getUser_name() + "\"\r\n" + "},\r\n"
							+ "{\r\n" + "\"type\": \"text\",\r\n" + "\"text\": \"10000\"\r\n" + "}\r\n" + "],\r\n"
							+ "\"templateId\": \"" + template_id + "\",\r\n" + "\"langCode\": \"en\"\r\n" + "}\r\n"
							+ "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}";

					// text has static content

					return staticTextTemplate;

				}
				// Static Image Template
				else if (list.get(2).toString().equals(template_id)) {

					String url = templateMessageApiRepository.getMediaUrl(template_id);

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + details.getNumber() + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"mediaTemplate\",\r\n" + "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \"" + url
							+ "\",\r\n" + "\"contentType\": \"image/jpeg\",\r\n" + "\"template\": \"" + template_id
							+ "\",\r\n" + "\"parameters\": {\r\n" + "},\r\n" + "\"langCode\": \"en\"\r\n" + "}\r\n"
							+ "}\r\n" + "],\r\n" + "\"responseType\":\"json\"\r\n" + "}";

					return staticTextTemplate;

				}
				// Dynamic Image Template
				else if (list.get(3).toString().equals(template_id)) {

					String url = templateMessageApiRepository.getMediaUrl(template_id);

					staticTextTemplate = "{\r\n" + "    \"messages\": [\r\n" + "{\r\n"
							+ "\"sender\": \"918010968371\",\r\n" + "\"to\": \"" + details.getNumber() + "\",\r\n"
							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \"" + url + "\",\r\n"
							+ "\"contentType\": \"image/jpeg\",\r\n" + "\"template\": \"test_msg_img_dynamic\",\r\n"
							+ "\"parameters\": {\r\n" + "\"text1\": \"" + details.getUser_name() + "\",\r\n"
							+ "\"text2\": \"Test\"\r\n" + "},\r\n" + "\"langCode\": \"en\"\r\n" + "}\r\n" + "}\r\n"
							+ "],\r\n" + "\"responseType\": \"json\"\r\n" + "}";

					// text2 has static content

					return staticTextTemplate;

				}
				// Static PDF Template
				else if (list.get(4).toString().equals(template_id)) {

					String url = templateMessageApiRepository.getMediaUrl(template_id);

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + details.getNumber() + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"mediaTemplate\",\r\n" + "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \"" + url
							+ "\",\r\n" + "\"contentType\": \"application/pdf\",\r\n"
							+ "\"filename\": \"IncomeTax-doc\",\r\n" + "\"template\": \"" + template_id + "\",\r\n"
							+ "\"parameters\": {},\r\n" + "\"langCode\": \"en\" } } ], \"responseType\":\"json\" }";

					// filename has static content

					return staticTextTemplate;

				}
				// Dynamic PDF Template
				else if (list.get(5).toString().equals(template_id)) {

					String url = templateMessageApiRepository.getMediaUrl(template_id);

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + details.getNumber() + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"mediaTemplate\",\r\n" + "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \"" + url
							+ "\",\r\n" + "\"contentType\": \"application/pdf\",\r\n"
							+ "\"filename\": \"IncomeTax-doc\",\r\n" + "\"template\": \"" + template_id + "\",\r\n"
							+ "\"parameters\": {\r\n" + "\"text1\": \"" + details.getUser_name() + "\",\r\n"
							+ "\"text2\": \"Test\"\r\n" + "},\r\n"
							+ "\"langCode\": \"en\" } } ], \"responseType\":\"json\" }";

					// filename has static content & text2 has also static content

					return staticTextTemplate;

				} else if (list.get(6).toString().equals(template_id)) {

				} else if (list.get(7).toString().equals(template_id)) {

				}
			} else {
				System.out.println("Template not found");
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return staticTextTemplate;
	}

	// Send Message Template to all database users..
	// Accepts (String, String)
	@Override
	public String getTextTemplateToAllUsers(String json, String template_id) {

		String staticTextTemplate = null;

		try {

			OkHttpClient client = new OkHttpClient(); // .newBuilder().build()

			MediaType JSON = MediaType.parse("application/json; charset=utf-8");

			boolean result = templateMessageApiRepository.existsById(template_id);

			List list = templateMessageApiRepository.getAllTemplateId();

			JSONObject jsonObject = new JSONObject(json);

			if (result) {
				
				String templateType = templateMessageApiRepository.getTemplateType(template_id);
				
				// Static text Template
				if (templateType.equals("static text")) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + jsonObject.get("Number") + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"template\",\r\n" + "\"template\": {\r\n" + "\"body\": [],\r\n"
							+ "\"templateId\": \"" + template_id + "\",\r\n" + "\"langCode\": \"en\"\r\n" + "}\r\n"
							+ "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}";

					return staticTextTemplate;
				}
				// Dynamic text Template
				else if (templateType.equals("dynamic text")) {

					String templateContent = templateMessageApiRepository.getTemplateContent(template_id);

					String pattern = "<([^>]+)>";
					Pattern r = Pattern.compile(pattern);
					Matcher m = r.matcher(templateContent);

					long totalDynamicVariables = m.results().count();

					List<String> temp = new ArrayList<>();

					try {

						for (int i = 1; i <= totalDynamicVariables; i++) {

							String body = "{  \"type\": \"text\", \"text\": \"" + jsonObject.get("var" + i) + "\"}  ";

							temp.add(body);
						}

						staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n"
								+ "\"sender\": \"918010968371\",\r\n" + "\"to\": \"" + jsonObject.get("Number")
								+ "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
								+ "\"channel\": \"wa\",\r\n" + "\"type\": \"template\",\r\n" + "\"template\": {\r\n"
								+ "\"body\": " + temp + " ,\r\n" + "\"templateId\": \"" + template_id + "\",\r\n"
								+ "\"langCode\": \"en\"\r\n" + "}\r\n" + "}\r\n" + "],\r\n"
								+ "\"responseType\": \"json\"\r\n" + "}";

						return staticTextTemplate;
					} catch (Exception e) {

						e.printStackTrace();
					}

				}
				// Static Image Template
				else if (list.get(2).toString().equals(template_id)) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + jsonObject.get("Number") + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"mediaTemplate\",\r\n" + "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
							+ jsonObject.get("Media Url") + "\",\r\n" + "\"contentType\": \"image/jpeg\",\r\n"
							+ "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": {\r\n" + "},\r\n"
							+ "\"langCode\": \"en\"\r\n" + "}\r\n" + "}\r\n" + "],\r\n"
							+ "\"responseType\":\"json\"\r\n" + "}";

					return staticTextTemplate;

				}
				
				// Dynamic Image Template
				else if (list.get(3).toString().equals(template_id)) {

					staticTextTemplate = "{\r\n" + "    \"messages\": [\r\n" + "{\r\n"
							+ "\"sender\": \"918010968371\",\r\n" + "\"to\": \"" + jsonObject.get("Number") + "\",\r\n"
							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \"" + jsonObject.get("Media Url") + "\",\r\n"
							+ "\"contentType\": \"image/jpeg\",\r\n" + "\"template\": \"test_msg_img_dynamic\",\r\n"
							+ "\"parameters\": {\r\n" + "\"text1\": \"" + jsonObject.get("var1") + "\",\r\n"
							+ "\"text2\": \"" + jsonObject.get("var2") + "\"\r\n" + "},\r\n"
							+ "\"langCode\": \"en\"\r\n" + "}\r\n" + "}\r\n" + "],\r\n"
							+ "\"responseType\": \"json\"\r\n" + "}";

					// text2 has static content

					return staticTextTemplate;

				}
				// Static PDF Template
				else if (list.get(4).toString().equals(template_id)) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + jsonObject.get("Number") + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"mediaTemplate\",\r\n" + "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
							+ jsonObject.get("Media Url") + "\",\r\n" + "\"contentType\": \"application/pdf\",\r\n"
							+ "\"filename\": \"File\",\r\n" + "\"template\": \"" + template_id + "\",\r\n"
							+ "\"parameters\": {},\r\n" + "\"langCode\": \"en\" } } ], \"responseType\":\"json\" }";

					// filename has static content

					return staticTextTemplate;
				}
				// Dynamic PDF Template
				else if (list.get(5).toString().equals(template_id)) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
							+ "\"to\": \"" + jsonObject.get("Number") + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
							+ "\"type\": \"mediaTemplate\",\r\n" + "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
							+ jsonObject.get("Media Url") + "\",\r\n" + "\"contentType\": \"application/pdf\",\r\n"
							+ "\"filename\": \"File\",\r\n" + "\"template\": \"" + template_id + "\",\r\n"
							+ "\"parameters\": {\r\n" + "\"text1\": \"" + jsonObject.get("var1") + "\",\r\n"
							+ "\"text2\": \"" + jsonObject.get("var2") + "\"\r\n" + "},\r\n"
							+ "\"langCode\": \"en\" } } ], \"responseType\":\"json\" }";

					// filename has static content & text2 has also static content

					return staticTextTemplate;

				}
				// Static Video Template
				else if (list.get(6).toString().equals(template_id)) {

					staticTextTemplate = "{\r\n" + "  \"messages\": [\r\n" + "    {\r\n"
							+ "      \"sender\": \"918010968371\",\r\n" + "      \"to\": \"" + jsonObject.get("Number")
							+ "\",\r\n" + "      \"messageId\": \"xxxxx\",\r\n"
							+ "      \"transactionId\": \"xxxxx\",\r\n" + "      \"channel\": \"wa\",\r\n"
							+ "      \"type\": \"mediaTemplate\",\r\n" + "      \"mediaTemplate\": {\r\n"
							+ "        \"mediaUrl\": \"" + jsonObject.get("Media Url") + "\",\r\n"
							+ "        \"contentType\": \"video/mp4\",\r\n" + "        \"template\": \"" + template_id
							+ "\",\r\n" + "        \"parameters\": {},\r\n" + "        \"langCode\": \"en\"\r\n"
							+ "      }\r\n" + "    }\r\n" + "  ],\r\n" + "  \"responseType\": \"json\"\r\n" + "}";

					return staticTextTemplate;

				}
				// Dynamic Video Template
				else if (list.get(7).toString().equals(template_id)) {

					staticTextTemplate = "{\r\n" + "  \"messages\": [\r\n" + "    {\r\n"
							+ "      \"sender\": \"918010968371\",\r\n" + "      \"to\": \"" + jsonObject.get("Number")
							+ "\",\r\n" + "      \"messageId\": \"xxxxx\",\r\n"
							+ "      \"transactionId\": \"xxxxx\",\r\n" + "      \"channel\": \"wa\",\r\n"
							+ "      \"type\": \"mediaTemplate\",\r\n" + "      \"mediaTemplate\": {\r\n"
							+ "        \"mediaUrl\": \"" + jsonObject.get("Media Url") + "\",\r\n"
							+ "        \"contentType\": \"video/mp4\",\r\n" + "        \"template\": \"" + template_id
							+ "\",\r\n" + "        \"parameters\": {\r\n" + "        			\"text1\" : \""
							+ jsonObject.get("var1") + "\",\r\n" + "            		\"text2\" : \""
							+ jsonObject.get("var2") + "\"\r\n" + "        },\r\n" + "        \"langCode\": \"en\"\r\n"
							+ "      }\r\n" + "    }\r\n" + "  ],\r\n" + "  \"responseType\": \"json\"\r\n" + "}";

					return staticTextTemplate;

				}
			} else {
				System.out.println("Template not found");
			}
		} catch (Exception e) {
			System.err.println("Something went Wrong..");
//			e.printStackTrace();
		}
		return staticTextTemplate;
	}

	@Override
	public List<String> getAllTemplates() {

		List list = templateMessageApiRepository.getAllTemplateId();

		return list;
	}

	@Override
	public String getTemplateContent(String template_id) {

		return templateMessageApiRepository.getTemplateContent(template_id);
	}

	@Override
	public String getTextTemplateToRequestPayload(String json, String template_id) {

		String staticTextTemplate = null;

		try {

			OkHttpClient client = new OkHttpClient(); // .newBuilder().build()

			MediaType JSON = MediaType.parse("application/json; charset=utf-8");

			boolean result = templateMessageApiRepository.existsById(template_id);

			List list = templateMessageApiRepository.getAllTemplateId();

			String s = list.get(0).toString();

			JSONObject jsonObject = new JSONObject(json);

//			if (result) {
//				// Static text Template
//				if (list.get(0).toString().equals(template_id)) {
//
//					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
//							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
//							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"template\",\r\n" + "\"template\": {\r\n"
//							+ "\"body\": [],\r\n" + "\"templateId\": \"" + template_id + "\",\r\n" + "\"langCode\": \""
//							+ new JSONObject(jsonObject.get("template").toString()).get("langCode") + "\"\r\n" + "}\r\n"
//							+ "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}" + "static";
//
//					return staticTextTemplate;
//				}
//				// Dynamic text Template
//				else if (list.get(1).toString().equals(template_id)) {
//
//					// new JSONObject(jsonObject.get("body").toString()).get("text2").toString()
//
//					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
//							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
//							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"template\",\r\n" + "\"template\": {\r\n"
//							+ "\"body\": [\r\n" + "{\r\n" + "\"type\": \"text\",\r\n" + "\"text\": \""
//							+ new JSONObject(
//									new JSONObject(jsonObject.get("template").toString()).get("body").toString())
//									.get("text1").toString()
//							+ "\"\r\n" + "},\r\n" + "{\r\n" + "\"type\": \"text\",\r\n" + "\"text\": \""
//							+ new JSONObject(
//									new JSONObject(jsonObject.get("template").toString()).get("body").toString())
//									.get("text2").toString()
//							+ "\"\r\n" + "}\r\n" + "],\r\n" + "\"templateId\": \"" + template_id + "\",\r\n"
//							+ "\"langCode\": \"" + new JSONObject(jsonObject.get("template").toString()).get("langCode")
//							+ "\"\r\n" + "}\r\n" + "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}"
//							+ "dynamic";
//
//					// text has static content
//
//					return staticTextTemplate;
//				}
//				// Static Image Template
//				else if (list.get(2).toString().equals(template_id)) {
//
////					String url = templateMessageApiRepository.getMediaUrl(template_id); // fetch URL from database
//
//					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
//							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
//							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
//							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl") + "\",\r\n"
//							+ "\"contentType\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType") + "\",\r\n"
//							+ "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": {\r\n" + "},\r\n"
//							+ "\"langCode\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode") + "\"\r\n"
//							+ "}\r\n" + "}\r\n" + "],\r\n" + "\"responseType\":\"json\"\r\n" + "}" + "static";
//
//					return staticTextTemplate;
//
//				}
//				// Dynamic Image Template
//				else if (list.get(3).toString().equals(template_id)) {
//
////					String url = templateMessageApiRepository.getMediaUrl(template_id); // fetch URL from database
//
//					staticTextTemplate = "{\r\n" + "    \"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
//							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
//							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
//							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl") + "\",\r\n"
//							+ "\"contentType\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType") + "\",\r\n"
//							+ "\"template\": \"test_msg_img_dynamic\",\r\n" + "\"parameters\": {\r\n" + "\"text1\": \""
//							+ new JSONObject(
//									new JSONObject(jsonObject.get("mediaTemplate").toString()).get("body").toString())
//									.get("text1").toString()
//							+ "\",\r\n" + "\"text2\": \""
//							+ new JSONObject(
//									new JSONObject(jsonObject.get("mediaTemplate").toString()).get("body").toString())
//									.get("text2").toString()
//							+ "\"\r\n" + "},\r\n" + "\"langCode\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode") + "\"\r\n"
//							+ "}\r\n" + "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}" + "dynamic";
//
//					// text2 has static content
//
//					return staticTextTemplate;
//
//				}
//				// Static PDF Template
//				else if (list.get(4).toString().equals(template_id)) {
//
////					String url = templateMessageApiRepository.getMediaUrl(template_id);
//
//					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
//							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
//							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
//							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl") + "\",\r\n"
//							+ "\"contentType\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType") + "\",\r\n"
//							+ "\"filename\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("filename").toString()
//							+ "\",\r\n" + "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": {},\r\n"
//							+ "\"langCode\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode")
//							+ "\" } } ], \"responseType\":\"json\" }" + "static";
//
//					// filename has static content
//
//					return staticTextTemplate;
//
//				}
//				// Dynamic PDF Template
//				else if (list.get(5).toString().equals(template_id)) {
//
////					String url = templateMessageApiRepository.getMediaUrl(template_id);
//
//					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
//							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
//							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
//							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl") + "\",\r\n"
//							+ "\"contentType\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType") + "\",\r\n"
//							+ "\"filename\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("filename").toString()
//							+ "\",\r\n" + "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": {\r\n"
//							+ "\"text1\": \""
//							+ new JSONObject(
//									new JSONObject(jsonObject.get("mediaTemplate").toString()).get("body").toString())
//									.get("text1").toString()
//							+ "\",\r\n" + "\"text2\": \""
//							+ new JSONObject(
//									new JSONObject(jsonObject.get("mediaTemplate").toString()).get("body").toString())
//									.get("text2").toString()
//							+ "\"\r\n" + "},\r\n" + "\"langCode\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode")
//							+ "\" } } ], \"responseType\":\"json\" }" + "dynamic";
//
//					// filename has static content & text2 has also static content
//
//					return staticTextTemplate;
//
//				}
//				// Static Video Template
//				else if (list.get(6).toString().equals(template_id)) {
//
//					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
//							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
//							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
//							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl") + "\",\r\n"
//							+ "\"contentType\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType") + "\",\r\n"
//							+ "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": {\r\n" + "},\r\n"
//							+ "\"langCode\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode") + "\"\r\n"
//							+ "}\r\n" + "}\r\n" + "],\r\n" + "\"responseType\":\"json\"\r\n" + "}" + "static";
//
//					return staticTextTemplate;
//
//				}
//				// Dynamic Video Template
//				else if (list.get(7).toString().equals(template_id)) {
//
//					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
//							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
//							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
//							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl") + "\",\r\n"
//							+ "\"contentType\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType") + "\",\r\n"
//							+ "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": {\r\n" + "\"text1\": \""
//							+ new JSONObject(
//									new JSONObject(jsonObject.get("mediaTemplate").toString()).get("body").toString())
//									.get("text1").toString()
//							+ "\",\r\n" + "\"text2\": \""
//							+ new JSONObject(
//									new JSONObject(jsonObject.get("mediaTemplate").toString()).get("body").toString())
//									.get("text2").toString()
//							+ "\"\r\n" + "},\r\n" + "\"langCode\": \""
//							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode")
//							+ "\" } } ], \"responseType\":\"json\" }" + "dynamic";
//
//					// filename has static content & text2 has also static content
//
//					return staticTextTemplate;
//
//				}
//			} 

			if (result) {

				String templateType = templateMessageApiRepository.getTemplateType(template_id);
				// Static text Template
				if (templateType.equals("static text")) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"template\",\r\n" + "\"template\": {\r\n"
							+ "\"body\": [],\r\n" + "\"templateId\": \"" + template_id + "\",\r\n" + "\"langCode\": \""
							+ new JSONObject(jsonObject.get("template").toString()).get("langCode") + "\"\r\n" + "}\r\n"
							+ "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}" + "static";

					return staticTextTemplate;

				}
				// Dynamic text Template
				else if (templateType.equals("dynamic text")) {

					String templateContent = templateMessageApiRepository.getTemplateContent(template_id);

					String pattern = "<([^>]+)>";
					Pattern r = Pattern.compile(pattern);
					Matcher m = r.matcher(templateContent);

					long totalDynamicVariables = m.results().count();

					List<String> temp = new ArrayList<>();

					try {

						if (new JSONObject(new JSONObject(jsonObject.get("template").toString()).get("body").toString())
								.length() == totalDynamicVariables) {

							for (int i = 1; i <= totalDynamicVariables; i++) {

								String body = "{  \"type\": \"text\", \"text\": \"" + new JSONObject(
										new JSONObject(jsonObject.get("template").toString()).get("body").toString())
										.get("text" + i).toString() + "\"}  ";

								temp.add(body);
							}

							staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
									+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number")
									+ "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
									+ "\"channel\": \"wa\",\r\n" + "\"type\": \"template\",\r\n" + "\"template\": {\r\n"
									+ "\"body\": " + temp + " ,\r\n" + "\"templateId\": \"" + template_id + "\",\r\n"
									+ "\"langCode\": \""
									+ new JSONObject(jsonObject.get("template").toString()).get("langCode") + "\"\r\n"
									+ "}\r\n" + "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}" + "dynamic";

							return staticTextTemplate;
						} else {
							return "Parameter error";
						}
					} catch (Exception e) {

						return "Parameter error";

					}

				}
				// Static Media Template
				else if (templateType.equals("static media") && !jsonObject.toString().contains("filename")) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl") + "\",\r\n"
							+ "\"contentType\": \""
							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType") + "\",\r\n"
							+ "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": {\r\n" + "},\r\n"
							+ "\"langCode\": \""
							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode") + "\"\r\n"
							+ "}\r\n" + "}\r\n" + "],\r\n" + "\"responseType\":\"json\"\r\n" + "}" + "static";

					return staticTextTemplate;

				}
				// Dynamic Media Template
				else if (templateType.equals("dynamic media") && !jsonObject.toString().contains("filename")) {

					String templateContent = templateMessageApiRepository.getTemplateContent(template_id);

					String pattern = "<([^>]+)>";
					Pattern r = Pattern.compile(pattern);
					Matcher m = r.matcher(templateContent);

					long totalDynamicVariables = m.results().count();

					List<String> temp = new ArrayList<>();

					try {

						if (new JSONObject(
								new JSONObject(jsonObject.get("mediaTemplate").toString()).get("body").toString())
								.length() == totalDynamicVariables) {

							String body;

							for (int i = 1; i <= totalDynamicVariables; i++) {

								body = "  \"text" + i + "\": \""
										+ new JSONObject(new JSONObject(jsonObject.get("mediaTemplate").toString())
												.get("body").toString()).get("text" + i).toString()
										+ "\" ";

								temp.add(body);
							}

							String data = "{" + temp.toString().replace("[", "").replace("]", "") + "}";

							staticTextTemplate = "{\r\n" + "    \"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
									+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number")
									+ "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
									+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
									+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
									+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl")
									+ "\",\r\n" + "\"contentType\": \""
									+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType")
									+ "\",\r\n" + "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": "
									+ data + " ,\r\n" + "\"langCode\": \""
									+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode")
									+ "\"\r\n" + "}\r\n" + "}\r\n" + "],\r\n" + "\"responseType\": \"json\"\r\n" + "}"
									+ "dynamic";

							return staticTextTemplate;
						} else {
							return "Parameter error";
						}
					} catch (Exception e) {

						return "Parameter error";

					}

				}
				// Static Document Media Template
				else if (jsonObject.toString().contains("filename") && templateType.equals("static media")) {

					staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
							+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number") + "\",\r\n"
							+ "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
							+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
							+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl") + "\",\r\n"
							+ "\"contentType\": \""
							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType") + "\",\r\n"
							+ "\"filename\": \""
							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("filename").toString()
							+ "\",\r\n" + "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": {},\r\n"
							+ "\"langCode\": \""
							+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode")
							+ "\" } } ], \"responseType\":\"json\" }" + "static";

					return staticTextTemplate;

				}
				// Dynamic Document Media Template
				else if (jsonObject.toString().contains("filename") && templateType.equals("dynamic media")) {

					String templateContent = templateMessageApiRepository.getTemplateContent(template_id);

					String pattern = "<([^>]+)>";
					Pattern r = Pattern.compile(pattern);
					Matcher m = r.matcher(templateContent);

					long totalDynamicVariables = m.results().count();

					List<String> temp = new ArrayList<>();

					try {

						if (new JSONObject(
								new JSONObject(jsonObject.get("mediaTemplate").toString()).get("body").toString())
								.length() == totalDynamicVariables) {

							String body;

							for (int i = 1; i <= totalDynamicVariables; i++) {

								body = "  \"text" + i + "\": \""
										+ new JSONObject(new JSONObject(jsonObject.get("mediaTemplate").toString())
												.get("body").toString()).get("text" + i).toString()
										+ "\" ";

								temp.add(body);
							}

							String data = "{" + temp.toString().replace("[", "").replace("]", "") + "}";

							staticTextTemplate = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \""
									+ jsonObject.get("sender") + "\",\r\n" + "\"to\": \"" + jsonObject.get("number")
									+ "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
									+ "\"channel\": \"wa\",\r\n" + "\"type\": \"mediaTemplate\",\r\n"
									+ "\"mediaTemplate\": {\r\n" + "\"mediaUrl\": \""
									+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("mediaUrl")
									+ "\",\r\n" + "\"contentType\": \""
									+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("contentType")
									+ "\",\r\n" + "\"filename\": \""
									+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("filename")
											.toString()
									+ "\",\r\n" + "\"template\": \"" + template_id + "\",\r\n" + "\"parameters\": "
									+ data + " ,\r\n" + "\"langCode\": \""
									+ new JSONObject(jsonObject.get("mediaTemplate").toString()).get("langCode")
									+ "\" } } ], \"responseType\":\"json\" }" + "dynamic";

							return staticTextTemplate;
						} else {
							return "Parameter error";
						}
					} catch (Exception e) {

						return "Parameter error";

					}

				}

			} else {

				return "false";

			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return staticTextTemplate;
	}

	@Override
	public String getTemplateType(String template_id) {

		return templateMessageApiRepository.getTemplateType(template_id);
	}

}
