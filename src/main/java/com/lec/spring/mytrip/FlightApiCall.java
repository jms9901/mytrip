package com.lec.spring.mytrip;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.mytrip.form.flight.FlightRoundTrip;
import com.lec.spring.mytrip.form.flight.FlightRoundTripInfo;
import com.lec.spring.mytrip.form.flight.FlightRoundTripResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FlightApiCall {

//    @Value("${api.flight.key}") 실 서비스 단에서 바꾸죠
//    private String apiKey;
private String apiKey = "6400a15222msh8627a40b3bd3531p1bdef5jsnaa784d38dcf3";

    // api 최초 호출
    public FlightRoundTripResponse fetchFlightData(FlightRoundTrip flightRoundTrip) {
        System.out.println("fetchFlightData() 유틸 진입 확인");

        try {
            String roundTripUrl = buildRoundTripUrl(flightRoundTrip);
            System.out.println("구성된 roundTripUrl: " + roundTripUrl); // URL 생성 확인

            URL url = new URL(roundTripUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-RapidAPI-Key", apiKey);
            conn.setRequestProperty("X-RapidAPI-Host", "sky-scanner3.p.rapidapi.com");
            System.out.println("연결된 URL: " + url); // 최종 URL 연결 확인

            int responseCode = conn.getResponseCode();
            System.out.println("응답 코드 확인: " + responseCode); // 응답 코드 확인

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // API 응답 내용 출력
                System.out.println("API 응답 내용: " + response.toString());

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());
                JsonNode flightsNode = rootNode.path("data");

                // rootNode를 먼저 출력하여 구조 확인
                System.out.println("rootNode 내용: " + rootNode.toString());

                List<FlightRoundTripInfo> flights = parseFlights(flightsNode);



                FlightRoundTripResponse flightRoundTripResponse = new FlightRoundTripResponse();
                flightRoundTripResponse.setFlights(flights);
                return flightRoundTripResponse;
            } else {
                System.out.println("API 호출 실패. 응답 코드: " + responseCode); // 응답 코드가 성공적이지 않으면 출력
                throw new RuntimeException("API 호출 실패. 응답 코드: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("API 호출 중 예외 발생: " + e.getMessage()); // 예외 발생 시 메시지 출력
            throw new RuntimeException("API 호출 중 예외 발생", e);
        }
    }

    //API 쿼리 URL 구성
    private String buildRoundTripUrl(FlightRoundTrip flightRoundTrip) throws Exception {

        System.out.println(flightRoundTrip.getFromAirportId());


        return "https://sky-scanner3.p.rapidapi.com/flights/search-roundtrip" +
                "?fromEntityId=" + URLEncoder.encode(flightRoundTrip.getFromAirportId(), "UTF-8") +
                "&toEntityId=" + URLEncoder.encode(flightRoundTrip.getToAirportId(), "UTF-8") +
                "&departDate=" + URLEncoder.encode(flightRoundTrip.getDepartDate(), "UTF-8") +
                "&returnDate=" + URLEncoder.encode(flightRoundTrip.getReturnDate(), "UTF-8") +
                "&stops=direct" +
                "&currency=KRW" +
                "&adults=" + flightRoundTrip.getAdultsHeadCnt() +
                "&children=" + flightRoundTrip.getChildrenHeadCnt() +
                "&infants=" + flightRoundTrip.getInfantsHeadCnt() +
                "&cabinClass=" + URLEncoder.encode(flightRoundTrip.getCabinClass(), "UTF-8");
    }

    // api 두번째 호출
    public FlightRoundTripResponse fetchincompleteData(String sessionId) {
        System.out.println("fetchFlightData() 유틸 진입 확인");

        try {
            String roundTripUrl = buildIncompleteUrl(sessionId);
            System.out.println("구성된 roundTripUrl: " + roundTripUrl); // URL 생성 확인

            URL url = new URL(roundTripUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-RapidAPI-Key", apiKey);
            conn.setRequestProperty("X-RapidAPI-Host", "sky-scanner3.p.rapidapi.com");
//            System.out.println("연결된 URL: " + url); // 최종 URL 연결 확인

            int responseCode = conn.getResponseCode();
            System.out.println("응답 코드 확인: " + responseCode); // 응답 코드 확인

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // API 응답 내용 출력
//                System.out.println("API 응답 내용: " + response.toString());

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());
                JsonNode flightsNode = rootNode.path("data");

                // rootNode를 먼저 출력하여 구조 확인
//                System.out.println("rootNode 내용: " + rootNode.toString());

                List<FlightRoundTripInfo> flights = parseFlights(flightsNode);

                FlightRoundTripResponse flightRoundTripResponse = new FlightRoundTripResponse();
                flightRoundTripResponse.setFlights(flights);
                return flightRoundTripResponse;
            } else {
                System.out.println("API 호출 실패. 응답 코드: " + responseCode); // 응답 코드가 성공적이지 않으면 출력
                throw new RuntimeException("API 호출 실패. 응답 코드: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("API 호출 중 예외 발생: " + e.getMessage()); // 예외 발생 시 메시지 출력
            throw new RuntimeException("API 호출 중 예외 발생", e);
        }
    }

    //API 쿼리 URL 구성
    private String buildIncompleteUrl(String sessionId) throws Exception {

        return "https://sky-scanner3.p.rapidapi.com/flights/search-incomplete" +
                "?sessionId=" + sessionId +
                "&stops=direct" +
                "&currency=KRW" +
                "&market=KR" +
                "&locale=ko-KR"
                ;
    }


    //  roundTrip Api 에서 필요한 값 추출
    private List<FlightRoundTripInfo> parseFlights(JsonNode flightsNode) {
        List<FlightRoundTripInfo> flights = new ArrayList<>();
        FlightRoundTripInfo flight = new FlightRoundTripInfo();

        //토큰과 세션 아이디, 그리고 status 추출. 토큰은 1번째 api 에서만 있음

        String token = "";
        if(!flightsNode.path("token").asText().isEmpty()){
            token = flightsNode.path("token").asText();
        }
        String sessionId = flightsNode.path("context").path("sessionId").asText();
        String callStatus = flightsNode.path("context").path("status").asText();


        flight.setToken(token);
        flight.setSessionId(sessionId);
        flight.setSessionId(callStatus);


        for (JsonNode flightNode : flightsNode.path("itineraries")) {
            String id = flightNode.path("id").asText();
            String price = flightNode.path("price").path("formatted").asText();
            String departure = flightNode.path("legs").get(0).path("departure").asText();

            flight.setId(id);
            flight.setPrice(price);
            flight.setDeparture(departure);

            System.out.println("여기, 유틸" + flight.toString());

            flights.add(flight);
        }

        return flights;
    }

    public List<Map<String, String>> fetchFlightDetail(String itineraryId, String token) {
        try {
            String detailUrl = buildDetailUrl(itineraryId, token);

            URL url = new URL(detailUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-RapidAPI-Key", apiKey);
            conn.setRequestProperty("X-RapidAPI-Host", "sky-scanner3.p.rapidapi.com");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("API 호출 실패. 응답 코드: " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());

            return parsePricingOptions(rootNode.path("data").path("itinerary").path("pricingOptions"));

        } catch (Exception e) {
            throw new RuntimeException("API 호출 중 오류 발생", e);
        }
    }

    private List<Map<String, String>> parsePricingOptions(JsonNode pricingOptionsNode) {
        List<Map<String, String>> pricingOptions = new ArrayList<>();

        for (JsonNode optionNode : pricingOptionsNode) {
            for (JsonNode agentNode : optionNode.path("agents")) {
                Map<String, String> option = new HashMap<>();
                option.put("siteName", agentNode.path("name").asText());
                option.put("price", "₩" + agentNode.path("price").asText());
                option.put("url", agentNode.path("url").asText());
                pricingOptions.add(option);
            }
        }
        System.out.println("pricingOptions: ApiCall" + pricingOptions);
        return pricingOptions;
    }

    private String buildDetailUrl(String itineraryId, String token) {
        try {
            return "https://sky-scanner3.p.rapidapi.com/flights/detail" +
                    "?itineraryId=" + URLEncoder.encode(itineraryId, "UTF-8") +
                    "&token=" + URLEncoder.encode(token, "UTF-8") +
                    "&stops=direct" + // 직항만 필터링
                    "&market=KR" +    // 마켓 정보 (KR: 한국)
                    "&currency=KRW" + // 화폐 정보 (KRW: 원화)
                    "&locale=ko-KR";  // 로케일 정보 (ko-KR: 한국어)
        } catch (Exception e) {
            throw new RuntimeException("URL 구성 중 오류 발생: " + e.getMessage(), e);
        }
    }




} // end class
