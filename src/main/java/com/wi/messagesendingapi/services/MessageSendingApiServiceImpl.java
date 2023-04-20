package com.wi.messagesendingapi.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wi.excelreadapi.entities.Details;
import com.wi.messagesendingapi.entities.MessageSendingApiEntity;
import com.wi.messagesendingapi.repository.MessageSendingApiRepository;

//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;

import com.squareup.okhttp.*;

//@Component
@Service
public class MessageSendingApiServiceImpl implements MessageSendingApiService {

	@Autowired
	MessageSendingApiRepository messageSendingApiRepository;

	@Override
	public String saveRecord(MessageSendingApiEntity messageSendingApiEntity) {

		MessageSendingApiEntity result = messageSendingApiRepository.save(messageSendingApiEntity);
//		System.out.println(result);

		if (result != null) {
			return "Record added successfully";
		}
		return null;

	}

//	@Override
//	public String sendMessageById(Details details) {
//
//		String returnmsg = null;
//
//		OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//		try {
//
//			Pattern pattern = Pattern.compile("\\d{10}");
//			Matcher matcher = pattern.matcher(details.getNumber().replace("+91", ""));
//
//			Pattern my_pattern = Pattern.compile("[^a-zA-Z ]", Pattern.CASE_INSENSITIVE);
//			Matcher my_match = my_pattern.matcher(details.getUser_name());
//
//			MessageSendingApiEntity messageSendingApiEntity;
//
//			String requestJson = null;
//
//			requestJson = "{\"name\" : \"" + details.getUser_name() + "\",\"number\" : \"" + details.getNumber()
//					 + "\" }";
//
//			if (!my_match.find() && !details.getNumber().equals("")) {
//
//				if (details.getNumber().length() == 13 && details.getNumber().contains("+91")
//						&& !StringUtils.isAlpha(details.getNumber()) && matcher.matches()) {
//
//					String jsonBody = "{\r\n" + "\"messages\": [\r\n" + "{\r\n" + "\"sender\": \"918010968371\",\r\n"
//							+ "\"to\": \"" + details.getNumber() + "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n"
//							+ "\"transactionId\": \"xxxxx\",\r\n" + "\"channel\": \"wa\",\r\n"
//							+ "\"type\": \"template\",\r\n" + "\"template\": {\r\n" + "\"body\": [],\r\n"
//							+ "\"templateId\": \"welcome_test\",\r\n" + "\"langCode\": \"en\"\r\n" + "}\r\n" + "}\r\n"
//							+ "],\r\n" + "\"responseType\": \"json\"\r\n" + "}";
//
//					RequestBody body = RequestBody.create(JSON, jsonBody);
//
//					Request request = new Request.Builder().addHeader("Content-Type", "application/json")
//							.addHeader("user", "acltest18").addHeader("pass", "acltest18")
//							.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages").post(body)
//							.build();
//
//					Response response = client.newCall(request).execute();
//
//					String result = response.body().string();
//
//					messageSendingApiEntity = new MessageSendingApiEntity(requestJson, result + "", "TRUE");
//
//					MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);
//
//					returnmsg = "Record added successfully";
//
//				} else {
//					messageSendingApiEntity = new MessageSendingApiEntity(requestJson,
//							"{\"success\": \"false\",\"description\": [{\"errorDescription\": \"INVALID WAID\"}]}" + "",
//							"FALSE");
//					messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
//
//					returnmsg = "Invalid number..";
//				}
//			}
//
//			else {
//				messageSendingApiEntity = new MessageSendingApiEntity(requestJson,
//						"{\"success\": \"false\",\"description\": [{\"errorDescription\": \"INVALID WAID\"}]}" + "",
//						"FALSE");
//				messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
//
//				returnmsg = "Please give the correct required fields.";
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	// Send message template to particular database user using user_id & template_id
	// Trial
	@Override
	public String sendMessageById(Details details, String template_id) {

		String returnmsg = null;

		OkHttpClient client = new OkHttpClient();		// .newBuilder().build()

		MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		try {

			Pattern pattern = Pattern.compile("\\d{10}");
			Matcher matcher = pattern.matcher(details.getNumber().replace("+91", ""));

			Pattern my_pattern = Pattern.compile("[^a-zA-Z ]", Pattern.CASE_INSENSITIVE);
			Matcher my_match = my_pattern.matcher(details.getUser_name());

			MessageSendingApiEntity messageSendingApiEntity;

			String requestJson = null;

			RestTemplate restTemplate = new RestTemplate();
			String jsonBody = restTemplate.postForObject(
					"http://localhost:8080/templatemessageapi/templates/getTextTemplate/{template_id}", details,
					String.class, template_id); // 2

			JSONObject convert_response = new JSONObject(jsonBody);

			JSONArray jsonArray = new JSONArray(convert_response.get("messages").toString());
			JSONObject convert_response1 = new JSONObject(jsonArray.get(0).toString());

			String templateId = null;

			String langCode = null;

			String body = null;

			String parameters = null;

			if (jsonBody.contains("body")) {

				templateId = new JSONObject(convert_response1.get("template").toString()).get("templateId").toString();
				parameters = new JSONObject(convert_response1.get("template").toString()).get("body").toString();
				langCode = new JSONObject(convert_response1.get("template").toString()).get("langCode").toString();
			} else {
				templateId = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("template")
						.toString();
				parameters = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("parameters")
						.toString();
				langCode = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("langCode").toString();
			}

			JSONObject result1 = null;

			RequestBody requestBody = RequestBody.create(JSON, jsonBody);

			Request request = new Request.Builder().addHeader("Content-Type", "application/json")
					.addHeader("user", "acltest18").addHeader("pass", "acltest18")
					.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages").post(requestBody)
					.build();

			if (!my_match.find() && !details.getNumber().equals("")) {

				if (details.getNumber().length() == 13 && details.getNumber().contains("+91")
						&& !StringUtils.isAlpha(details.getNumber()) && matcher.matches()) {

					Response response = client.newCall(request).execute();

					String result = response.body().string();

					result1 = new JSONObject(result);

					messageSendingApiEntity = new MessageSendingApiEntity(details.getUser_name(), details.getNumber(),
							convert_response1.getString("sender"), convert_response1.getString("channel"),
							convert_response1.getString("type"), parameters, templateId, langCode,
							convert_response.getString("responseType"), result1.get("success").toString(),
							result1.get("responseId").toString());

					MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);

				} else {

					Response response = client.newCall(request).execute();

					String result = response.body().string();

					result1 = new JSONObject(result);

					messageSendingApiEntity = new MessageSendingApiEntity(details.getUser_name(), details.getNumber(),
							convert_response1.getString("sender"), convert_response1.getString("channel"),
							convert_response1.getString("type"), parameters, templateId, langCode,
							convert_response.getString("responseType"), "false", null);
					messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
				}
			}

			else {
				messageSendingApiEntity = new MessageSendingApiEntity(details.getUser_name(), details.getNumber(),
						convert_response1.getString("sender"), convert_response1.getString("channel"),
						convert_response1.getString("type"), parameters, templateId, langCode,
						convert_response.getString("responseType"), "false", null);
				messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Trial
//	@Override
//	public String sendMessageAll(String json) {
//
//		OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//		try {
//
//			JSONArray jsonArray = new JSONArray(json);
//
//			JSONObject jsonObject = null;
//
//			for (Object object : jsonArray) {
//				jsonObject = new JSONObject(object.toString());
//
//				// validate number is 10 digit
//				Pattern pattern = Pattern.compile("\\d{10}");
//				Matcher matcher = pattern.matcher((jsonObject.get("number").toString().replace("+91", "")));
//
//				// validate name does not contain digits/special character
//				Pattern my_pattern = Pattern.compile("[^a-zA-Z ]", Pattern.CASE_INSENSITIVE);
//				Matcher my_match = my_pattern.matcher((jsonObject.get("user_name").toString()));
////			boolean check = my_match.find();
//
//				MessageSendingApiEntity messageSendingApiEntity;
//
//				String requestJson = null;
//
//				requestJson = "{\"name\" : \"" + jsonObject.get("user_name") + "\",\"number\" : \""
//						+ jsonObject.get("number") + "\" }";
//
//				if (!my_match.find() && !jsonObject.get("number").toString().equals("")) {
//
//					if (jsonObject.get("number").toString().length() == 13
//							&& jsonObject.get("number").toString().contains("+91")
//							&& !StringUtils.isAlpha(jsonObject.get("number").toString()) && matcher.matches()) {
//
//						String jsonBody = "{\r\n" + "\"messages\": [\r\n" + "{\r\n"
//								+ "\"sender\": \"918010968371\",\r\n" + "\"to\": \"" + jsonObject.get("number")
//								+ "\",\r\n" + "\"messageId\": \"xxxxx\",\r\n" + "\"transactionId\": \"xxxxx\",\r\n"
//								+ "\"channel\": \"wa\",\r\n" + "\"type\": \"template\",\r\n" + "\"template\": {\r\n"
//								+ "\"body\": [],\r\n" + "\"templateId\": \"welcome_test\",\r\n"
//								+ "\"langCode\": \"en\"\r\n" + "}\r\n" + "}\r\n" + "],\r\n"
//								+ "\"responseType\": \"json\"\r\n" + "}";
//
//						RequestBody body = RequestBody.create(JSON, jsonBody);
//
//						Request request = new Request.Builder().addHeader("Content-Type", "application/json")
//								.addHeader("user", "acltest18").addHeader("pass", "acltest18")
//								.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages").post(body)
//								.build();
//
//						Response response = client.newCall(request).execute();
//
//						String result;
//
//						result = response.body().string();
//
//						messageSendingApiEntity = new MessageSendingApiEntity(requestJson, result + "", "TRUE");
//
//						MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);
//
//					} else {
//						messageSendingApiEntity = new MessageSendingApiEntity(requestJson,
//								"{\"success\": \"false\",\"description\": [{\"errorDescription\": \"INVALID WAID\"}]}"
//										+ "",
//								"FALSE");
//						messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
//
//					}
//				} else {
//					messageSendingApiEntity = new MessageSendingApiEntity(requestJson,
//							"{\"success\": \"false\",\"description\": [{\"errorDescription\": \"INVALID WAID\"}]}" + "",
//							"FALSE");
//					messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
//
//				}
//
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	// Send message template to all database users using template_id Trial 1.0
	@Override
	public String sendMessageAll(String json, String template_id) {

		OkHttpClient client = new OkHttpClient();		// .newBuilder().build()

		MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		try {

			JSONArray jsonArray = new JSONArray(json);

			JSONObject jsonObject = null;

			for (Object object : jsonArray) {
				jsonObject = new JSONObject(object.toString());

				Pattern pattern = Pattern.compile("\\d{10}");
				Matcher matcher = pattern.matcher(jsonObject.get("number").toString().replace("+91", ""));

				Pattern my_pattern = Pattern.compile("[^a-zA-Z ]", Pattern.CASE_INSENSITIVE);
				Matcher my_match = my_pattern.matcher(jsonObject.get("user_name").toString());

				MessageSendingApiEntity messageSendingApiEntity;

				String requestJson = null;

				RestTemplate restTemplate = new RestTemplate();
				String jsonBody = restTemplate.postForObject(
						"http://localhost:8080/templatemessageapi/templates/getTextTemplateToAllUsers/{template_id}",
						jsonObject.toString(), String.class, template_id); // 2

				JSONObject convert_response = new JSONObject(jsonBody);

				JSONArray jsonArray1 = new JSONArray(convert_response.get("messages").toString());
				JSONObject convert_response1 = new JSONObject(jsonArray1.get(0).toString());

				String templateId = null;

				String langCode = null;

				String body = null;

				String parameters = null;

				if (jsonBody.contains("body")) {

					templateId = new JSONObject(convert_response1.get("template").toString()).get("templateId")
							.toString();
					parameters = new JSONObject(convert_response1.get("template").toString()).get("body").toString();
					langCode = new JSONObject(convert_response1.get("template").toString()).get("langCode").toString();
				} else {
					templateId = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("template")
							.toString();
					parameters = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("parameters")
							.toString();
					langCode = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("langCode")
							.toString();
				}

				JSONObject result1 = null;

				RequestBody requestBody = RequestBody.create(JSON, jsonBody);

				Request request = new Request.Builder().addHeader("Content-Type", "application/json")
						.addHeader("user", "acltest18").addHeader("pass", "acltest18")
						.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages").post(requestBody)
						.build();

				if (!my_match.find() && !jsonObject.get("number").toString().equals("")) {

					if (jsonObject.get("number").toString().length() == 13
							&& jsonObject.get("number").toString().contains("+91")
							&& !StringUtils.isAlpha(jsonObject.get("number").toString()) && matcher.matches()) {

						Response response = client.newCall(request).execute();

						String result = response.body().string();

						result1 = new JSONObject(result);

						messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("user_name").toString(),
								jsonObject.get("number").toString(), convert_response1.getString("sender"),
								convert_response1.getString("channel"), convert_response1.getString("type"), parameters,
								templateId, langCode, convert_response.getString("responseType"),
								result1.get("success").toString(), result1.get("responseId").toString());

						MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);

					} else {

						Response response = client.newCall(request).execute();

						String result = response.body().string();

						result1 = new JSONObject(result);

						messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("user_name").toString(),
								jsonObject.get("number").toString(), convert_response1.getString("sender"),
								convert_response1.getString("channel"), convert_response1.getString("type"), parameters,
								templateId, langCode, convert_response.getString("responseType"), "false", null);
						messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
					}
				}

				else {
					messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("user_name").toString(),
							jsonObject.get("number").toString(), convert_response1.getString("sender"),
							convert_response1.getString("channel"), convert_response1.getString("type"), parameters,
							templateId, langCode, convert_response.getString("responseType"), "false", null);
					messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

//
////	@Override
////	public List<MessageSendingApiEntity> getAllRecords() {
////		
//////		return messageSendingApiRepository.findAll();
////		return messageSendingApiRepository.findAll();
////	}
//
//	@Override
//	public Optional<MessageSendingApiEntity> getRecordById(Long id) {
//
//		return messageSendingApiRepository.findById(id);
//	}
//
//	@Override
//	public List<MessageSendingApiDto> getAllRecords() {
////	public String getAllRecords() {
//
//		Iterable<MessageSendingApiEntity> iterable = messageSendingApiRepository.findAll();
//
//		List<MessageSendingApiDto> mApiDtos = StreamSupport.stream(iterable.spliterator(), false).map(mApi -> {
//			MessageSendingApiDto dto = new MessageSendingApiDto();
//			BeanUtils.copyProperties(mApi, dto);
//			return dto;
//		}).collect(Collectors.toList());
//
//		String result = "";
//
//		for (int i = 0; i < mApiDtos.size(); i++) {
//			String data = mApiDtos.get(i).getRequest_json();
//			JSONObject json = new JSONObject(data);
//			System.out.println(json.getString("name").toString() + " " + json.getString("number").toString());
//			result += json.toString();
//		}
//
////		result = mApiDtos.toString().replace("[MessageSendingApiDto ", "")
////				.replace("]]", "]")
////				.replace("MessageSendingApiDto ", "");
//
////		if (result.contains("1")) {
////			System.out.println("Welcome!!");
////		} else {
////			System.out.println("Bye..");
////		}
//
//		return mApiDtos;
//	}
//

	// Send message template to all Excel users using template_id
	@Override
	public String sendMessageToExcelUser(String json, String template_id) throws IOException {

		OkHttpClient client = new OkHttpClient();		// .newBuilder().build()

		String returnmsg = null;

		MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		try {

			JSONArray jsonArray = new JSONArray(json);

			JSONObject jsonObject = null;

			for (Object object : jsonArray) {
				jsonObject = new JSONObject(object.toString());

				String number = jsonObject.get("Number").toString().replace("E9", "").replace(".", "");

				String num = "+91" + number;

//				jsonObject = new JSONObject("{\r\n" + "      \"user_name\": \"" + jsonObject.get("user_name").toString()
//						+ "\",\r\n" + "      \"number\": \"" + num + "\",\r\n" + "      }".toString());

				Pattern pattern = Pattern.compile("\\d{10}");
				Matcher matcher = pattern.matcher(jsonObject.get("Number").toString().replace("+91", ""));

				Pattern my_pattern = Pattern.compile("[^a-zA-Z ]", Pattern.CASE_INSENSITIVE);
				Matcher my_match = my_pattern.matcher(jsonObject.get("User Name").toString());

				MessageSendingApiEntity messageSendingApiEntity;

				String requestJson = null;

				RestTemplate restTemplate = new RestTemplate();
				String jsonBody = restTemplate.postForObject(
						"http://localhost:8080/templatemessageapi/templates/getTextTemplateToAllUsers/{template_id}",
						jsonObject.toString(), String.class, template_id); // 2

				String templateContent = restTemplate.postForObject(
						"http://localhost:8080/templatemessageapi/templates/getTemplateContent/{template_id}", null,
						String.class, template_id); // 2

				JSONObject convert_response = new JSONObject(jsonBody);

				JSONArray jsonArray1 = new JSONArray(convert_response.get("messages").toString());
				JSONObject convert_response1 = new JSONObject(jsonArray1.get(0).toString());

				String templateId = null;

				String langCode = null;

				String body = null;

				String parameters = null, mediaUrl = null, contentType = null;

				if (jsonBody.contains("body")) {

					templateId = new JSONObject(convert_response1.get("template").toString()).get("templateId")
							.toString();
					parameters = new JSONObject(convert_response1.get("template").toString()).get("body").toString();
					langCode = new JSONObject(convert_response1.get("template").toString()).get("langCode").toString();
					
					
					JSONObject result1 = null;

					RequestBody requestBody = RequestBody.create(JSON, jsonBody);

					Request request = new Request.Builder().addHeader("Content-Type", "application/json")
							.addHeader("user", "acltest18").addHeader("pass", "acltest18")
							.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages").post(requestBody)
							.build();

					if (!my_match.find() && !number.equals("")) {

						if (num.length() == 13
//								&& jsonObject.get("number").toString().contains("+91")
								&& !StringUtils.isAlpha(num) && matcher.matches()) {

							Response response = client.newCall(request).execute();

							String result = response.body().string();

							result1 = new JSONObject(result);

							messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
									num, convert_response1.getString("sender"), convert_response1.getString("channel"),
									convert_response1.getString("type"), null, templateId, null, langCode,
									convert_response.getString("responseType"), result1.get("success").toString(),
									result1.get("responseId").toString());

							MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);

						} else {

							Response response = client.newCall(request).execute();

							String result = response.body().string();

							result1 = new JSONObject(result);

							messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
									"+91" + number.replaceAll("E(.*)", ""), convert_response1.getString("sender"),
									convert_response1.getString("channel"), convert_response1.getString("type"), null,
									templateId, null, langCode, convert_response.getString("responseType"), "false", null);

							messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
						}
					}

					else {
						messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
								"+91" + number.replaceAll("E(.*)", ""), convert_response1.getString("sender"),
								convert_response1.getString("channel"), convert_response1.getString("type"), null,
								templateId, null, langCode, convert_response.getString("responseType"), "false", null);

						messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
					}
					
					
					
				} else {
					templateId = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("template")
							.toString();

					mediaUrl = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("mediaUrl")
							.toString();

					contentType = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("contentType")
							.toString();

					parameters = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("parameters")
							.toString();
					langCode = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("langCode")
							.toString();
					
					
					JSONObject result1 = null;

					RequestBody requestBody = RequestBody.create(JSON, jsonBody);

					Request request = new Request.Builder().addHeader("Content-Type", "application/json")
							.addHeader("user", "acltest18").addHeader("pass", "acltest18")
							.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages").post(requestBody)
							.build();

					if (!my_match.find() && !number.equals("")) {

						if (num.length() == 13
//								&& jsonObject.get("number").toString().contains("+91")
								&& !StringUtils.isAlpha(num) && matcher.matches()) {

							Response response = client.newCall(request).execute();

							String result = response.body().string();

							result1 = new JSONObject(result);

//							messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
//									num, convert_response1.getString("sender"), convert_response1.getString("channel"),
//									convert_response1.getString("type"), null, templateId, null, langCode,
//									convert_response.getString("responseType"), result1.get("success").toString(),
//									result1.get("responseId").toString());

							messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
									num, convert_response1.getString("sender"),
									convert_response1.getString("channel"), convert_response1.getString("type"),
									null, templateId, null, mediaUrl, contentType, langCode,
									convert_response.getString("responseType"), result1.get("success").toString(),
									result1.get("responseId").toString());

							MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);

						} else {

							Response response = client.newCall(request).execute();

							String result = response.body().string();

							result1 = new JSONObject(result);

//							messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
//									"+91" + number.replaceAll("E(.*)", ""), convert_response1.getString("sender"),
//									convert_response1.getString("channel"), convert_response1.getString("type"), null,
//									templateId, null, langCode, convert_response.getString("responseType"), "false", null);
							
							messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
									num, convert_response1.getString("sender"),
									convert_response1.getString("channel"), convert_response1.getString("type"),
									null, templateId, null, mediaUrl, contentType, langCode,
									convert_response.getString("responseType"), "false", null);

							messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
						}
					}

					else {
						
						messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
								num, convert_response1.getString("sender"),
								convert_response1.getString("channel"), convert_response1.getString("type"),
								null, templateId, null, mediaUrl, contentType, langCode,
								convert_response.getString("responseType"), "false", null);

						messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
					}
					
					
					
				}

//				JSONObject result1 = null;
//
//				RequestBody requestBody = RequestBody.create(JSON, jsonBody);
//
//				Request request = new Request.Builder().addHeader("Content-Type", "application/json")
//						.addHeader("user", "acltest18").addHeader("pass", "acltest18")
//						.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages").post(requestBody)
//						.build();
//
//				if (!my_match.find() && !number.equals("")) {
//
//					if (num.length() == 13
////							&& jsonObject.get("number").toString().contains("+91")
//							&& !StringUtils.isAlpha(num) && matcher.matches()) {
//
//						Response response = client.newCall(request).execute();
//
//						String result = response.body().string();
//
//						result1 = new JSONObject(result);
//
//						messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
//								num, convert_response1.getString("sender"), convert_response1.getString("channel"),
//								convert_response1.getString("type"), null, templateId, null, langCode,
//								convert_response.getString("responseType"), result1.get("success").toString(),
//								result1.get("responseId").toString());
//
////						messageSendingApiEntity = new MessageSendingApiEntity(null,
////								jsonObject.get("number").toString(), convert_response1.getString("sender"),
////								convert_response1.getString("channel"), convert_response1.getString("type"),
////								parameters, templateId, templateContent, mediaUrl, contentType, langCode,
////								convert_response.getString("responseType"), result1.get("success").toString(),
////								result1.get("responseId").toString());
//
//						MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);
//
//					} else {
//
//						Response response = client.newCall(request).execute();
//
//						String result = response.body().string();
//
//						result1 = new JSONObject(result);
//
//						messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
//								"+91" + number.replaceAll("E(.*)", ""), convert_response1.getString("sender"),
//								convert_response1.getString("channel"), convert_response1.getString("type"), null,
//								templateId, null, langCode, convert_response.getString("responseType"), "false", null);
//
//						messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
//					}
//				}
//
//				else {
//					messageSendingApiEntity = new MessageSendingApiEntity(jsonObject.get("User Name").toString(),
//							"+91" + number.replaceAll("E(.*)", ""), convert_response1.getString("sender"),
//							convert_response1.getString("channel"), convert_response1.getString("type"), null,
//							templateId, null, langCode, convert_response.getString("responseType"), "false", null);
//
//					messageSendingApiEntity = messageSendingApiRepository.save(messageSendingApiEntity);
//				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Integer> getUpdatedRecords(int total_excel_records) {

		List<String> list = messageSendingApiRepository.getUpdatedStatus(total_excel_records);

		int status_true_count = 0, status_false_count = 0;

		for (String status : list) {

			if (status.equals("true")) {
				status_true_count++;
			} else {
				status_false_count++;
			}
		}

		List<Integer> total_count_list = new ArrayList<>();

		// total_count_list[ status_true_count, status_false_count ]

		total_count_list.add(status_true_count);
		total_count_list.add(status_false_count);

//		System.out.println("List = " + total_count_list);

		return total_count_list;
	}

	// Send Message by request payload
	@Override
	public String sendMessageToRequestpayload(String json) {

		OkHttpClient client = new OkHttpClient();		// .newBuilder().build()

		MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		JSONObject result1 = null;

		try {

			JSONArray jsonArray = new JSONArray(json);

			JSONObject jsonObject = null;

			String number = null;

			for (Object object : jsonArray) {
				jsonObject = new JSONObject(object.toString());

				if (jsonObject.get("number").toString().equals("")) {

					return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
							+ "	\"errorDescription\" : \"Missing Phone Number\"\r\n" + "}";

				}

				else {

					Pattern pattern = Pattern.compile("\\d{10}");
//				Matcher matcher = pattern.matcher(jsonObject.get("number").toString().replace("91", ""));
					Matcher matcher = null;

					if (jsonObject.get("number").toString().substring(0, 2).equals("91")) {

						matcher = pattern.matcher(jsonObject.get("number").toString().replaceFirst("91", ""));

						number = "+" + jsonObject.get("number").toString();
					} else if (jsonObject.get("number").toString().substring(0, 3).equals("+91")) {

						matcher = pattern.matcher(jsonObject.get("number").toString().replace("+91", ""));

						number = jsonObject.get("number").toString();
					} else {

						number = "+91" + jsonObject.get("number").toString();

						matcher = pattern.matcher(number.replace("+91", ""));
					}

					MessageSendingApiEntity messageSendingApiEntity;

					String requestJson = null, jsonBody = null, templateContent = null;

					String templateId = null;

					String langCode = null;

					String body = null;

					String parameters = null, mediaUrl = null, contentType = null;

					RestTemplate restTemplate = new RestTemplate();

					// if block for text messages
					if (jsonObject.get("type").toString().equals("template")) {

						// checking Template ID is missing
						if (new JSONObject(jsonObject.get("template").toString()).get("templateId").toString()
								.equals("")) {

							return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
									+ "	\"errorDescription\" : \"Missing Template ID\"\r\n" + "}";

						}

						jsonBody = restTemplate.postForObject(
								"http://localhost:8080/templatemessageapi/templates/getTextTemplateToRequestPayload/{template_id}",
								jsonObject.toString(), String.class,
								new JSONObject(jsonObject.get("template").toString()).get("templateId").toString()); // 2

						if (jsonBody.equals("false")) {

							return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
									+ "	\"errorDescription\" : \"Invalid Template ID\"\r\n" + "}";

						}
						else if(jsonBody.equals("Parameter error")) {
							
							return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
									+ "	\"errorDescription\" : \"Error in body parameter\"\r\n" + "}";
							
						}
						else {
							templateContent = restTemplate.postForObject(
									"http://localhost:8080/templatemessageapi/templates/getTemplateContent/{template_id}",
									null, String.class,
									new JSONObject(jsonObject.get("template").toString()).get("templateId").toString()); // 2

							JSONObject convert_response = new JSONObject(jsonBody);

							JSONArray jsonArray1 = new JSONArray(convert_response.get("messages").toString());
							JSONObject convert_response1 = new JSONObject(jsonArray1.get(0).toString());

							templateId = new JSONObject(convert_response1.get("template").toString()).get("templateId")
									.toString();

							parameters = new JSONObject(convert_response1.get("template").toString()).get("body")
									.toString();

							langCode = new JSONObject(convert_response1.get("template").toString()).get("langCode")
									.toString();

							RequestBody requestBody = RequestBody.create(JSON, jsonBody);

							Request request = new Request.Builder().addHeader("Content-Type", "application/json")
									.addHeader("user", "acltest18").addHeader("pass", "acltest18")
									.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages")
									.post(requestBody).build();

							if (!jsonObject.get("number").toString().equals("")) {

//						if (jsonObject.get("number").toString().length() == 13
//								&& jsonObject.get("number").toString().contains("+91")
//								&& !StringUtils.isAlpha(jsonObject.get("number").toString()) && matcher.matches()) {

								if (number.length() == 13 && number.contains("+91") && !StringUtils.isAlpha(number)
										&& matcher.matches()) {

									Response response = client.newCall(request).execute();

									String result = response.body().string();

									result1 = new JSONObject(result);

									if (jsonBody.contains("static")) {

										messageSendingApiEntity = new MessageSendingApiEntity(null, number,
												convert_response1.getString("sender"),
												convert_response1.getString("channel"),
												convert_response1.getString("type"), null, templateId, null, langCode,
												convert_response.getString("responseType"),
												result1.get("success").toString(),
												result1.get("responseId").toString());

										MessageSendingApiEntity savedData = messageSendingApiRepository
												.save(messageSendingApiEntity);

//								return "Message Sent Successfully";

										return "{\r\n" + "	\"status\" : \"SUCCESS\"\r\n" + "}";

									} else if (jsonBody.contains("dynamic")) {

										messageSendingApiEntity = new MessageSendingApiEntity(
												new JSONObject(new JSONObject(jsonObject.get("template").toString())
														.get("body").toString()).get("text1").toString(),
												number, convert_response1.getString("sender"),
												convert_response1.getString("channel"),
												convert_response1.getString("type"), null, templateId, null, langCode,
												convert_response.getString("responseType"),
												result1.get("success").toString(),
												result1.get("responseId").toString());

										MessageSendingApiEntity savedData = messageSendingApiRepository
												.save(messageSendingApiEntity);

//								return "Message Sent Successfully";

										return "{\r\n" + "	\"status\" : \"SUCCESS\"\r\n" + "}";

									}

								} else {

									Response response = client.newCall(request).execute();

									String result = response.body().string();

									result1 = new JSONObject(result);

									if (jsonBody.contains("static")) {

										messageSendingApiEntity = new MessageSendingApiEntity(null, number,
												convert_response1.getString("sender"),
												convert_response1.getString("channel"),
												convert_response1.getString("type"), null, templateId, null, langCode,
												convert_response.getString("responseType"), "false", null);

										MessageSendingApiEntity savedData = messageSendingApiRepository
												.save(messageSendingApiEntity);

//								return "INVALID NUMBER";

										return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
												+ "	\"errorDescription\" : \"INVALID WAID\"\r\n" + "}";

									} else if (jsonBody.contains("dynamic")) {

										messageSendingApiEntity = new MessageSendingApiEntity(
												new JSONObject(new JSONObject(jsonObject.get("template").toString())
														.get("body").toString()).get("text1").toString(),
												number, convert_response1.getString("sender"),
												convert_response1.getString("channel"),
												convert_response1.getString("type"), null, templateId, null, langCode,
												convert_response.getString("responseType"), "false", null);

										MessageSendingApiEntity savedData = messageSendingApiRepository
												.save(messageSendingApiEntity);

//								return "INVALID NUMBER";

										return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
												+ "	\"errorDescription\" : \"INVALID WAID\"\r\n" + "}";

									}
								}
							}

							else {
								if (jsonBody.contains("static")) {

									messageSendingApiEntity = new MessageSendingApiEntity(null, number,
											convert_response1.getString("sender"),
											convert_response1.getString("channel"), convert_response1.getString("type"),
											null, templateId, null, langCode,
											convert_response.getString("responseType"), "false", null);

									MessageSendingApiEntity savedData = messageSendingApiRepository
											.save(messageSendingApiEntity);

//							return "INVALID NUMBER";

									return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
											+ "	\"errorDescription\" : \"INVALID WAID\"\r\n" + "}";

								} else if (jsonBody.contains("dynamic")) {

									messageSendingApiEntity = new MessageSendingApiEntity(
											new JSONObject(new JSONObject(jsonObject.get("template").toString())
													.get("body").toString()).get("text1").toString(),
											number, convert_response1.getString("sender"),
											convert_response1.getString("channel"), convert_response1.getString("type"),
											null, templateId, null, langCode,
											convert_response.getString("responseType"), "false", null);

									MessageSendingApiEntity savedData = messageSendingApiRepository
											.save(messageSendingApiEntity);

//							return "INVALID NUMBER";

									return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
											+ "	\"errorDescription\" : \"INVALID WAID\"\r\n" + "}";

								}
							}
						}
					}

					// Else block for media messages
					else {
						// checking Template ID is missing
						if (new JSONObject(jsonObject.get("mediaTemplate").toString()).get("templateId").toString()
								.equals("")) {

							return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
									+ "	\"errorDescription\" : \"Missing Template ID\"\r\n" + "}";

						}

						jsonBody = restTemplate.postForObject(
								"http://localhost:8080/templatemessageapi/templates/getTextTemplateToRequestPayload/{template_id}",
								jsonObject.toString(), String.class,
								new JSONObject(jsonObject.get("mediaTemplate").toString()).get("templateId")
										.toString()); // 2

						if (jsonBody.equals("false")) {

							return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
									+ "	\"errorDescription\" : \"Invalid Template ID\"\r\n" + "}";

						}
						else if(jsonBody.equals("Parameter error")) {
							
							return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
									+ "	\"errorDescription\" : \"Error in body parameter\"\r\n" + "}";
							
						}
						else {
							templateContent = restTemplate.postForObject(
									"http://localhost:8080/templatemessageapi/templates/getTemplateContent/{template_id}",
									null, String.class, new JSONObject(jsonObject.get("mediaTemplate").toString())
											.get("templateId").toString()); // 2

							JSONObject convert_response = new JSONObject(jsonBody);

							JSONArray jsonArray1 = new JSONArray(convert_response.get("messages").toString());
							JSONObject convert_response1 = new JSONObject(jsonArray1.get(0).toString());

							templateId = new JSONObject(convert_response1.get("mediaTemplate").toString())
									.get("template").toString();

							mediaUrl = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("mediaUrl")
									.toString();

							contentType = new JSONObject(convert_response1.get("mediaTemplate").toString())
									.get("contentType").toString();

							parameters = new JSONObject(convert_response1.get("mediaTemplate").toString())
									.get("parameters").toString();
							langCode = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("langCode")
									.toString();

							RequestBody requestBody = RequestBody.create(JSON, jsonBody);

							Request request = new Request.Builder().addHeader("Content-Type", "application/json")
									.addHeader("user", "acltest18").addHeader("pass", "acltest18")
									.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages")
									.post(requestBody).build();

							if (!jsonObject.get("number").toString().equals("")) {

//						if (jsonObject.get("number").toString().length() == 13
//								&& jsonObject.get("number").toString().contains("+91")
//								&& !StringUtils.isAlpha(jsonObject.get("number").toString()) && matcher.matches()) {

								if (number.length() == 13 && number.contains("+91") && !StringUtils.isAlpha(number)
										&& matcher.matches()) {

									Response response = client.newCall(request).execute();

									String result = response.body().string();

									result1 = new JSONObject(result);
										
//									
									if (result1.toString().contains("errorDescription") || result1.toString().contains("error_description") ) {

										// Check Is media type supported or not
										if(result1.toString().contains("errorDescription") ) {
											
													return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
															+ "	\"errorDescription\" : \""
															+ new JSONArray(result1.get("description").toString()).getJSONObject(0)
																	.get("errorDescription")
															+ "\"\r\n" + "}";
										}
											// Check Is sender number is valid or not
										else if(result1.toString().contains("error_description")) {
											
											return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
													+ "	\"errorDescription\" : \""
													+ result1.get("error")
													+ "\"\r\n" + "}";
											
										}

									}

									if (jsonBody.contains("static")) {

										messageSendingApiEntity = new MessageSendingApiEntity(null, number,
												convert_response1.getString("sender"),
												convert_response1.getString("channel"),
												convert_response1.getString("type"), null, templateId, null, mediaUrl,
												contentType, langCode, convert_response.getString("responseType"),
												result1.get("success").toString(),
												result1.get("responseId").toString());

										MessageSendingApiEntity savedData = messageSendingApiRepository
												.save(messageSendingApiEntity);

//								return "Message Sent Successfully";

										return "{\r\n" + "	\"status\" : \"SUCCESS\"\r\n" + "}";

									} else if (jsonBody.contains("dynamic")) {

										messageSendingApiEntity = new MessageSendingApiEntity(
												new JSONObject(
														new JSONObject(jsonObject.get("mediaTemplate").toString())
																.get("body").toString())
														.get("text1").toString(),
												number, convert_response1.getString("sender"),
												convert_response1.getString("channel"),
												convert_response1.getString("type"), null, templateId, null, mediaUrl,
												contentType, langCode, convert_response.getString("responseType"),
												result1.get("success").toString(),
												result1.get("responseId").toString());

										MessageSendingApiEntity savedData = messageSendingApiRepository
												.save(messageSendingApiEntity);

//								return "Message Sent Successfully";

										return "{\r\n" + "	\"status\" : \"SUCCESS\"\r\n" + "}";

									}

								} else {

									Response response = client.newCall(request).execute();

									String result = response.body().string();

									result1 = new JSONObject(result);

									if (jsonBody.contains("static")) {

										messageSendingApiEntity = new MessageSendingApiEntity(null, number,
												convert_response1.getString("sender"),
												convert_response1.getString("channel"),
												convert_response1.getString("type"), null, templateId, null, mediaUrl,
												contentType, langCode, convert_response.getString("responseType"),
												"false", null);

										MessageSendingApiEntity savedData = messageSendingApiRepository
												.save(messageSendingApiEntity);

//								return "INVALID NUMBER";

										return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
												+ "	\"errorDescription\" : \"INVALID WAID\"\r\n" + "}";

									} else if (jsonBody.contains("dynamic")) {

										messageSendingApiEntity = new MessageSendingApiEntity(
												new JSONObject(
														new JSONObject(jsonObject.get("mediaTemplate").toString())
																.get("body").toString())
														.get("text1").toString(),
												number, convert_response1.getString("sender"),
												convert_response1.getString("channel"),
												convert_response1.getString("type"), null, templateId, null, mediaUrl,
												contentType, langCode, convert_response.getString("responseType"),
												"false", null);

										MessageSendingApiEntity savedData = messageSendingApiRepository
												.save(messageSendingApiEntity);

//								return "INVALID NUMBER";

										return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
												+ "	\"errorDescription\" : \"INVALID WAID\"\r\n" + "}";

									}

								}
							}

							else {
								if (jsonBody.contains("static")) {

									messageSendingApiEntity = new MessageSendingApiEntity(null, number,
											convert_response1.getString("sender"),
											convert_response1.getString("channel"), convert_response1.getString("type"),
											null, templateId, null, mediaUrl, contentType, langCode,
											convert_response.getString("responseType"), "false", null);

									MessageSendingApiEntity savedData = messageSendingApiRepository
											.save(messageSendingApiEntity);

//							return "INVALID NUMBER";

									return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
											+ "	\"errorDescription\" : \"INVALID WAID\"\r\n" + "}";

								} else if (jsonBody.contains("dynamic")) {

									messageSendingApiEntity = new MessageSendingApiEntity(
											new JSONObject(new JSONObject(jsonObject.get("mediaTemplate").toString())
													.get("body").toString()).get("text1").toString(),
											number, convert_response1.getString("sender"),
											convert_response1.getString("channel"), convert_response1.getString("type"),
											null, templateId, null, mediaUrl, contentType, langCode,
											convert_response.getString("responseType"), "false", null);

									MessageSendingApiEntity savedData = messageSendingApiRepository
											.save(messageSendingApiEntity);

//							return "INVALID NUMBER";

									return "{\r\n" + "	\"status\" : \"FAILURE\",\r\n"
											+ "	\"errorDescription\" : \"INVALID WAID\"\r\n" + "}";

								}

							}
						}
					}

//				JSONObject convert_response = new JSONObject(jsonBody);
//
//				JSONArray jsonArray1 = new JSONArray(convert_response.get("messages").toString());
//				JSONObject convert_response1 = new JSONObject(jsonArray1.get(0).toString());

//				String templateId = null;
//
//				String langCode = null;
//
//				String body = null;
//
//				String parameters = null, mediaUrl = null, contentType = null;

//				if (jsonBody.contains("body")) {
//
//					templateId = new JSONObject(convert_response1.get("template").toString()).get("templateId")
//							.toString();
//					
//					parameters = new JSONObject(convert_response1.get("template").toString()).get("body").toString();
//					
//					langCode = new JSONObject(convert_response1.get("template").toString()).get("langCode").toString();
//				} else {
//					templateId = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("template")
//							.toString();
//
//					mediaUrl = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("mediaUrl")
//							.toString();
//					
//					contentType = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("contentType")
//							.toString();
//					
//					parameters = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("parameters")
//							.toString();
//					langCode = new JSONObject(convert_response1.get("mediaTemplate").toString()).get("langCode")
//							.toString();
//				}

//				result1 = null;

//				RequestBody requestBody = RequestBody.create(JSON, jsonBody);
//
//				Request request = new Request.Builder().addHeader("Content-Type", "application/json")
//						.addHeader("user", "acltest18").addHeader("pass", "acltest18")
//						.url("https://pushuat.aclwhatsapp.com/pull-platform-receiver/wa/messages").post(requestBody)
//						.build();
//
//				if (!jsonObject.get("number").toString().equals("")) {
//
//					if (jsonObject.get("number").toString().length() == 13
//							&& jsonObject.get("number").toString().contains("+91")
//							&& !StringUtils.isAlpha(jsonObject.get("number").toString()) && matcher.matches()) {
//
//						Response response = client.newCall(request).execute();
//
//						String result = response.body().string();
//
//						result1 = new JSONObject(result);
//
//						if (jsonBody.contains("static")) {
//
//							messageSendingApiEntity = new MessageSendingApiEntity(null,
//									jsonObject.get("number").toString(), convert_response1.getString("sender"),
//									convert_response1.getString("channel"), convert_response1.getString("type"),
//									parameters, templateId, templateContent, mediaUrl, contentType, langCode,
//									convert_response.getString("responseType"), result1.get("success").toString(),
//									result1.get("responseId").toString());
//
//							MessageSendingApiEntity savedData = messageSendingApiRepository
//									.save(messageSendingApiEntity);
//
//							return "Message Sent Successfully";
//
////							return result1;
//
//						} else if (jsonBody.contains("dynamic")) {
//
//							messageSendingApiEntity = new MessageSendingApiEntity(
//									new JSONObject(new JSONObject(jsonObject.get("template").toString()).get("body")
//											.toString()).get("text1").toString(),
//									jsonObject.get("number").toString(), convert_response1.getString("sender"),
//									convert_response1.getString("channel"), convert_response1.getString("type"),
//									parameters, templateId, templateContent, langCode,
//									convert_response.getString("responseType"), result1.get("success").toString(),
//									result1.get("responseId").toString());
//
//							MessageSendingApiEntity savedData = messageSendingApiRepository
//									.save(messageSendingApiEntity);
//
//							return "Message Sent Successfully";
////							return result1;
//
//						}
//
//					} else {
//
//						Response response = client.newCall(request).execute();
//
//						String result = response.body().string();
//
//						result1 = new JSONObject(result);
//
//						if (jsonBody.contains("static")) {
//
//							messageSendingApiEntity = new MessageSendingApiEntity(null,
//									jsonObject.get("number").toString(), convert_response1.getString("sender"),
//									convert_response1.getString("channel"), convert_response1.getString("type"),
//									parameters, templateId, templateContent, langCode,
//									convert_response.getString("responseType"), "false", null);
//
//							MessageSendingApiEntity savedData = messageSendingApiRepository
//									.save(messageSendingApiEntity);
//
//							return "INVALID NUMBER";
//
////							return result1;
//
//						} else if (jsonBody.contains("dynamic")) {
//
//							messageSendingApiEntity = new MessageSendingApiEntity(
//									new JSONObject(new JSONObject(jsonObject.get("template").toString()).get("body")
//											.toString()).get("text1").toString(),
//									jsonObject.get("number").toString(), convert_response1.getString("sender"),
//									convert_response1.getString("channel"), convert_response1.getString("type"),
//									parameters, templateId, templateContent, langCode,
//									convert_response.getString("responseType"), "false", null);
//
//							MessageSendingApiEntity savedData = messageSendingApiRepository
//									.save(messageSendingApiEntity);
//
//							return "INVALID NUMBER";
//
////							return result1;
//
//						}
//
//					}
//				}
//
//				else {
//					if (jsonBody.contains("static")) {
//
//						messageSendingApiEntity = new MessageSendingApiEntity(null, jsonObject.get("number").toString(),
//								convert_response1.getString("sender"), convert_response1.getString("channel"),
//								convert_response1.getString("type"), parameters, templateId, templateContent, langCode,
//								convert_response.getString("responseType"), "false", null);
//
//						MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);
//
//						return "INVALID NUMBER";
//
////						return result1;
//
//					} else if (jsonBody.contains("dynamic")) {
//
//						messageSendingApiEntity = new MessageSendingApiEntity(
//								new JSONObject(
//										new JSONObject(jsonObject.get("template").toString()).get("body").toString())
//										.get("text1").toString(),
//								jsonObject.get("number").toString(), convert_response1.getString("sender"),
//								convert_response1.getString("channel"), convert_response1.getString("type"), parameters,
//								templateId, templateContent, langCode, convert_response.getString("responseType"),
//								"false", null);
//
//						MessageSendingApiEntity savedData = messageSendingApiRepository.save(messageSendingApiEntity);
//
//						return "INVALID NUMBER";
//
////						return result1;
//
//					}
//
//				}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
