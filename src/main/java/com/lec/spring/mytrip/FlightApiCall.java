package com.lec.spring.mytrip;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.mytrip.form.flight.*;
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
import java.util.stream.Collectors;

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

        // 토큰과 세션 아이디, 그리고 status 추출
        String token = "";
        if (!flightsNode.path("token").asText().isEmpty()) {
            token = flightsNode.path("token").asText();
        }
        String sessionId = flightsNode.path("context").path("sessionId").asText();
        String callStatus = flightsNode.path("context").path("status").asText();

        for (JsonNode flightNode : flightsNode.path("itineraries")) {
            // 새로운 객체 생성
            FlightRoundTripInfo flight = new FlightRoundTripInfo();

            // 공통 값 설정
            flight.setToken(token);
            flight.setSessionId(sessionId);
            flight.setCallStatus(callStatus);

            // itinerary별 값 설정
            String id = flightNode.path("id").asText();
            String price = flightNode.path("price").path("formatted").asText();

            String outDeparture = flightNode.path("legs").get(0).path("departure").asText()
                    .split("T")[1];  // 가는 편 출발일시
            String returnDeparture = flightNode.path("legs").get(1).path("departure")
                    .asText().split("T")[1];;  // 오는 편 출발일시
            String outArrival = flightNode.path("legs").get(0).path("arrival").asText()
                    .split("T")[1];;; // 가는 편 도착일시
            String returnArrival = flightNode.path("legs").get(1).path("arrival").asText()
                    .split("T")[1];;; // 오는 편 도착일시

            String outDurationInMinutes = minToHourAndMin(flightNode.path("legs").get(0).path("durationInMinutes").asText()); // 가는 편 걸리는 시간(분)
            String returnDurationInMinutes = minToHourAndMin(flightNode.path("legs").get(1).path("durationInMinutes").asText()); // 오는 편 걸리는 시간(분)

            // 출발 도착의 경우, 가는 편만 기재. 오는 편에선 둘이 순서 바꾸기요 어차피 직항로만 구하는데 우리
            String originDisplayCode = flightNode.path("legs").get(0).path("origin").path("displayCode").asText(); // 출발공항코드 ex) ICN
            String originName = flightNode.path("legs").get(0).path("origin").path("name").asText(); // 출발 공항 이름
            String destinationDisplayCode = flightNode.path("legs").get(0).path("destination").path("displayCode").asText(); // 도착공항코드 ex) ICN
            String destinationName = flightNode.path("legs").get(0).path("destination").path("name").asText(); // 도착 공항 이름

            String outAirportName = flightNode.path("legs").get(0).path("carriers").path("marketing").get(0).path("name").asText(); // 출발 항공사이름
            String returnAirportName = flightNode.path("legs").get(1).path("carriers").path("marketing").get(0).path("name").asText(); // 도착 항공사이름
            String outLogoUrl = flightNode.path("legs").get(0).path("carriers").path("marketing").get(0).path("logoUrl").asText();  // 출발 항공사 로고
            String returnLogoUrl = flightNode.path("legs").get(1).path("carriers").path("marketing").get(0).path("logoUrl").asText();  // 도착 항공사 로고

            String outCity = flightNode.path("legs").get(0).path("origin").path("city").asText(); // 출발 도시
            String outCountry = flightNode.path("legs").get(0).path("origin").path("country").asText(); // 출발 국가
            String returnCity = flightNode.path("legs").get(1).path("origin").path("city").asText(); // 도착 도시
            String returnCountry = flightNode.path("legs").get(1).path("origin").path("country").asText(); // 도착 국가

            flight.setId(id);
            flight.setPrice(price);
            flight.setOutDeparture(outDeparture);
            flight.setReturnDeparture(returnDeparture);
            flight.setOutArrival(outArrival);
            flight.setOutDurationInMinutes(outDurationInMinutes);
            flight.setReturnDurationInMinutes(returnDurationInMinutes);
            flight.setOriginDisplayCode(originDisplayCode);
            flight.setOriginName(originName);
            flight.setDestinationDisplayCode(destinationDisplayCode);
            flight.setDestinationName(destinationName);
            flight.setOutAirportName(outAirportName);
            flight.setReturnAirportName(returnAirportName);
            flight.setOutLogoUrl(outLogoUrl);
            flight.setReturnLogoUrl(returnLogoUrl);
            flight.setOutCity(outCity);
            flight.setOutCountry(outCountry);
            flight.setReturnCity(returnCity);
            flight.setReturnCountry(returnCountry);

            System.out.println("여기, 유틸: " + flight.toString());

            // 리스트에 추가
            flights.add(flight);

            // 객체 참조 제거 (더 이상 사용되지 않도록 설정)
            flight = null;
        }

        return flights;
    }

    private String minToHourAndMin(String min){
        int minmin = Integer.parseInt(min);

        return minmin / 60 + "시간 " + minmin % 60 + "분";
    }

    public FlightDetailResponse fetchFlightDetail(String itineraryId, String token) {
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
            StringBuilder responseStringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseStringBuilder.append(inputLine);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseStringBuilder.toString());

            List<Map<String, String>> pricingOptions = parsePricingOptions(
                    rootNode.path("data").path("itinerary").path("pricingOptions")
            );

            // FlightDetailResponse 생성
            FlightDetailResponse response = new FlightDetailResponse();
            response.setDetails(
                    pricingOptions.stream()
                            .map(option -> {
                                FlightDetailInfo info = new FlightDetailInfo();
                                info.setSiteName(option.get("siteName"));
                                info.setPrice(option.get("price"));
                                info.setBookingUrl(option.get("url"));
                                return info;
                            })
                            .collect(Collectors.toList())
            );

            FlightDetailTicketInfo detailTicket = parseDetailTicket(
                    rootNode.path("data").path("itinerary").path("legs")
            );

            response.setTicket(detailTicket);

            System.out.println("ServiceImpl Detail : " + response);
            return response;

        } catch (Exception e) {
            throw new RuntimeException("API 호출 중 오류 발생", e);
        }
    }

    private FlightDetailTicketInfo parseDetailTicket(JsonNode path) {
        FlightDetailTicketInfo ticketInfo = new FlightDetailTicketInfo();

        // 출발 관련 정보 설정
        JsonNode outLeg = path.get(0); // 가는 편 데이터
        JsonNode outSegment = outLeg.path("segments").get(0);
        ticketInfo.setOutAirport(outSegment.path("origin").path("displayCode").asText());
        ticketInfo.setOutCity(outSegment.path("origin").path("city").asText());
        ticketInfo.setOutCountry(outSegment.path("origin").path("name").asText());

        ticketInfo.setOutDate(outSegment.path("departure").asText().split("T")[0]);
        ticketInfo.setOutTime(outSegment.path("departure").asText().split("T")[1].substring(0, 5));
        ticketInfo.setOutCarrier(outSegment.path("marketingCarrier").path("name").asText());
        ticketInfo.setOutCarrierLog(outSegment.path("marketingCarrier").path("logo").asText());

        // 도착 관련 정보 설정
        JsonNode returnLeg = path.get(1); // 오는 편 데이터
        JsonNode returnSegment = returnLeg.path("segments").get(0);
        ticketInfo.setReturnAirport(returnSegment.path("origin").path("displayCode").asText());
        ticketInfo.setReturnCity(returnSegment.path("origin").path("city").asText());
        ticketInfo.setReturnCountry(returnSegment.path("origin").path("name").asText());

        ticketInfo.setReturnDate(returnSegment.path("departure").asText().split("T")[0]);
        ticketInfo.setReturnTime(returnSegment.path("departure").asText().split("T")[1].substring(0, 5));
        ticketInfo.setReturnCarrier(returnSegment.path("marketingCarrier").path("name").asText());
        ticketInfo.setReturnCarrierLog(returnSegment.path("marketingCarrier").path("logo").asText());

        return ticketInfo;
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
