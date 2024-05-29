-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: localhost    Database: otcdb
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `hit` int DEFAULT NULL,
  `class_room_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `modified_at` datetime(6) NOT NULL,
  `title` varchar(1000) NOT NULL,
  `content` varchar(10000) NOT NULL,
  `article_type` enum('NOTICE','FREE','COUNSELING','HOBBY') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDXrmt77yibijtk4sarremr67saa` (`created_at`),
  KEY `FK9e64v59k021ngrlnx3uawiwy8` (`class_room_id`),
  KEY `FK6l9vkfd5ixw8o8kph5rj1k7gu` (`member_id`),
  CONSTRAINT `FK6l9vkfd5ixw8o8kph5rj1k7gu` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FK9e64v59k021ngrlnx3uawiwy8` FOREIGN KEY (`class_room_id`) REFERENCES `classroom` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (7,NULL,'2024-02-16 02:52:18.002141',1,1,'2024-02-16 09:46:12.668106','새로운 시작을 알리는 순간: 작은 교실 서비스 런칭!','<p>안녕하세요,</p><p>우리의 교육 커뮤니티에 새로운 변화의 바람이 불어오고 있습니다. 오랜 기간 동안의 준비와 노력 끝에, 드디어 \"작은 교실\" 서비스를 런칭하게 되었습니다.</p><p> 이 서비스는 학생들에게 더 개인화된 학습 경험을 제공하고, 교사들에게는 더 효과적으로 학생들과 소통할 수 있는 플랫폼을 제공할 것입니다.</p>','NOTICE'),(7,18,'2024-02-16 03:20:36.419133',2,11,'2024-02-16 09:45:43.817679','안녕하세요!','<p>안녕하세요! </p><p><br></p><p>새롭게 입학하게된 김덕배라고 합니다!</p><p><br></p><p>축구 좋아해요 ㅎㅎ </p><p><br></p><p>잘부탁드립니다!</p>','FREE'),(8,NULL,'2024-02-16 03:23:12.253001',3,11,'2024-02-16 03:57:23.115150','깻잎 논쟁 이야기','<p>다들 깻잎 논쟁에 대해서 어떻게 생각하세요??</p><p><br></p><p>저는 깻잎 떼주는거 괜찮다고 생각하는데 </p><p><br></p><p>그게 문제인가요??</p>','FREE'),(5,NULL,'2024-02-16 03:25:16.173077',4,11,'2024-02-16 03:58:49.315440','축구!','<p>혹시 축구 좋아하는 분 계실까요?</p><p><br></p><p>같이 축구 이야기 많이 하고싶어요!! </p><p><br></p><p>댓글달면 채팅걸게요 ㅎㅎ</p>','HOBBY'),(5,NULL,'2024-02-16 03:27:45.985207',5,12,'2024-02-16 06:27:42.799028','짝사랑','<p>좋아하는 남자가 생겼는데 ,,,</p><p><br></p><p>저랑 나이 차이가 너무 많이 나서 혹시나 저를 싫어하면 어떡하죠??</p><p><br></p><p>고백하고 싶은데 두려워요 ㅠㅠ 어떻게 해야할까요?</p>','COUNSELING'),(3,NULL,'2024-02-16 03:31:07.003068',6,13,'2024-02-16 03:58:30.366772','MBTI 가 ?','<p>다들 MBTI가 뭐예요?? </p><p><br></p><p>저는 ESFJ 인데 다른 분들 MBTI가 궁금해요 !</p><p><br></p><p>같이 공유해보아요!</p>','FREE'),(4,18,'2024-02-16 03:33:31.893125',7,8,'2024-02-16 09:10:31.256511','3반 여러분을 위한 중요한 안내','<p><span style=\"color: rgb(13, 13, 13);\">안녕하세요, 4학년 3반 여러분! 담임선생님 김대성입니다.</span></p><p><span style=\"color: rgb(13, 13, 13);\"> 새 학기가 시작되면서 여러분에게 몇 가지 중요한 사항을 공유하고자 합니다. </span></p><p><span style=\"color: rgb(13, 13, 13);\">잠시 시간을 내어 주의 깊게 읽어주시기 바랍니다.</span></p><p><br></p><h2><strong style=\"color: rgb(13, 13, 13);\">1. 특별 활동 및 프로젝트</strong></h2><p><span style=\"color: rgb(13, 13, 13);\">올해는 여러분이 참여할 수 있는 다양한 특별 활동과 프로젝트를 준비했습니다.</span></p><p><span style=\"color: rgb(13, 13, 13);\"> 새로운 경험을 통해 배우고 성장할 수 있는 기회가 될 것입니다. 관심 있는 활동에 적극적으로 참여해 보세요.</span></p><p><br></p><h2><strong style=\"color: rgb(13, 13, 13);\">2.학급 규칙 및 기대하는 행동</strong></h2><p><span style=\"color: rgb(13, 13, 13);\">우리 반만의 특별한 분위기를 만들기 위해, 함께 지켜야 할 규칙과 기대하는 행동에 대해 이야기하고자 합니다. </span></p><p><span style=\"color: rgb(13, 13, 13);\">서로 존중하고 배려하는 분위기 속에서 모두가 행복한 학교 생활을 할 수 있도록 여러분의 협력이 필요합니다.</span></p><p><br></p><p>새 학기가 여러분에게 즐겁고 의미 있는 시간이 되기를 바랍니다. 항상 열심히 하되, 건강도 잘 챙기며 학교 생활을 만끽해 주세요.</p><p>여러분과 함께할 이번 학기를 기대하며, 모두 화이팅입니다!</p><p>감사합니다.</p><p><br></p>','NOTICE'),(1,NULL,'2024-02-16 03:34:31.177398',8,14,'2024-02-16 03:58:10.173416','어제 손흥민 축구 봤어??','<p>ㅈㄱㄴ</p><p><br></p><p>진짜 흥민이형은 해외 파가 맞더라</p><p><br></p><p>클라스 지려</p>','FREE'),(3,NULL,'2024-02-16 03:37:24.466260',9,14,'2024-02-16 09:46:27.129564','내남편과 결혼해줘(스포주의)','<p>박민환 진짜 미친거아니야?</p><p><br></p><p>그리고 그렇게 쪽팔렸는데도 계속 다니고 싶나,,,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   </p>','FREE'),(4,NULL,'2024-02-16 03:42:23.581444',10,12,'2024-02-16 03:57:37.447359',' 나만의 소소한 행복, 여기서 나눌게','<p>안녕하세요, 커뮤니티 여러분!</p><p><br></p><p>오늘은 나만의 소소한 행복을 나누고 싶어서 글을 써봅니다. 요즘 나에게 크거나 작은 특별한 순간, 또는 새로운 경험 중에서 느낀 소감을 여러분과 함께 나누고 싶어졌어요.</p><p>가끔은 일상에서 눈여겨보지 못하는 작은 행복들이 있죠. 예를 들면, 햇살 가득한 창가에서 공부하며 느끼는 평화로움이나, 친구들과 함께 한 웃음 소리, 혹은 새로운 취미를 발견하고 그것을 통해 얻는 즐거움 등 말이죠.</p><p>여러분도 어떤 순간이나 경험에서 특별한 감동을 받았거나, 행복을 느꼈던 일이 있을 거라고 생각해요. 그런 소소한 행복을 여기서 함께 공유하면서, 우리 모두가 서로의 긍정적인 에너지를 나눌 수 있으면 좋겠어요.</p><p>댓글로 여러분의 소중한 순간을 나누어주세요. 작은 행복이 모여 더 큰 행복이 되듯, 우리 학교 커뮤니티도 여러분의 이야기로 더 풍요로워질 것 같아 기대돼요!</p><p>함께 행복을 나누어요! 🌈😊</p>','FREE'),(3,NULL,'2024-02-16 03:44:00.730161',11,2,'2024-02-16 09:47:25.662842','다꾸러들 모여라!','<p>혹시 다꾸 하시는 분들 있으신가요?</p><p>그럼 같이 매주 집에서 스봉 접어서 우편으로 전달하는거 어때요?</p><p>저는 감성 다꾸도 하고 빈티지도 합니다!</p>','HOBBY'),(5,NULL,'2024-02-16 03:45:34.855392',12,2,'2024-02-16 03:59:28.750839','요즘 지각을 자주 해요','<p>집에만 있어서 그럼지 아침이 온줄도 모르고 계속 자다가 늦게 일어나게 되네요 ㅠㅠ</p><p>저만 그런거 아니요??? 매일매일 늦잠자고 싶어요~!</p>','COUNSELING'),(5,NULL,'2024-02-16 03:46:36.461784',13,15,'2024-02-16 03:57:54.428912','맛집 추천','<p>안녕! </p><p>오늘은 역삼역근처의 맛집을 추천해볼까 해</p><p><br></p><ol><li>이도 곰탕</li><li>마구로젠</li><li>고갯마루</li></ol><p><br></p><p>한번 가서 먹어봐 ! 정말 맛있을거야,,!</p>','FREE'),(7,NULL,'2024-02-16 03:50:06.565498',14,11,'2024-02-16 03:57:35.912879','패딩 논쟁에 대해','<p>패딩은 잘못 된 거라 생각하는데</p><p><br></p><p>다른 친구들은 어떨지 궁금하네</p><p><br></p><p>만약 애인이 내친구의 패딩지퍼를 올려준다면?!?!</p>','FREE'),(5,NULL,'2024-02-16 03:51:54.030938',15,11,'2024-02-16 03:57:42.166228','애플 비전프로','<p>나도 애플 비전프로 써보고 싶어!!</p>','FREE'),(4,NULL,'2024-02-16 03:53:54.463116',16,13,'2024-02-16 06:27:37.302232','키보드 키캡 공동구매','<p>혹시 키보드 커스텀해서 쓰는 친구들 있을까?</p><p><br></p><p>나 커스텀해서 쓰는데 공구 할 사람 있으면 댓글 달아줘~!</p>','FREE'),(18,NULL,'2024-02-16 03:54:37.948468',17,13,'2024-02-16 03:58:47.291146','여자 아이들(G-IDLE) 신곡 어때?','<p>여자 아이들 신곡이 논란 이 많던데 </p><p><br></p><p>다들 어떻게 생각해?</p>','FREE'),(0,NULL,'2024-02-16 03:55:44.901014',18,12,'2024-02-16 03:55:44.901014','생일선물 추천','<p>안녕하세요</p><p><br></p><p>부모님이 생일선물로 갖고싶은걸 고르라는데요</p><p><br></p><p>뭘 고르는게 좋을지 고민이되요</p><p><br></p><p>생일선물을 추천해주세요!!</p>','FREE'),(3,NULL,'2024-02-16 05:32:43.749971',19,21,'2024-02-16 05:33:00.404699','강남 코노 같이갈사람!','<p>이번주 토요일에</p><p>점심 먹고 한 2시쯤? 가고싶은데 갈사람??</p><p>최대 4명정도까지 모아봅니당!</p>','COUNSELING'),(0,NULL,'2024-02-16 05:35:37.348673',20,22,'2024-02-16 05:35:37.348673','과제가 너무많다 ㅠㅠ','<p><span style=\"color: rgb(13, 13, 13);\">요즘 수학이랑 국어 과제가 너무 많아서 고민이야. </span></p><p><span style=\"color: rgb(13, 13, 13);\">어떻게 하면 효율적으로 공부하면서도 시간을 효과적으로 쓸 수 있을까?</span></p>','COUNSELING'),(0,NULL,'2024-02-16 05:37:12.123876',21,19,'2024-02-16 05:37:12.123876','고동현 인강 들어본사람?','<p>과학이 너무 약해서 사 보려는데 혹시 들은사람있나?</p><p><br></p><p>그리고 같이 들을 사람있으면 같이 부담해서 사자!</p><p>댓글줘!!</p>','COUNSELING'),(0,NULL,'2024-02-16 05:37:55.916531',22,19,'2024-02-16 05:37:55.916531','사진 모임은 없나?','<p>나는 사진찍는게 취미인데 모임 있으면 나가고싶어!</p><p><br></p><p>맛있는거 먹고 사진찍고 같이 카페가서 얘기나누고 하는 모임있으면 좋겠음!</p>','HOBBY'),(0,NULL,'2024-02-16 05:38:21.633388',23,19,'2024-02-16 05:38:21.633388','노래추천좀!','<p><span style=\"color: rgb(13, 13, 13);\">최근에 새로운 음악을 발견해서 계속 듣고 있어. </span></p><p><span style=\"color: rgb(13, 13, 13);\">추천해줄만한 곡 있으면 알려줘!</span></p>','FREE'),(0,NULL,'2024-02-16 05:39:48.194257',24,21,'2024-02-16 05:39:48.194257','최근 6개월간 밤 10시 칼수면 해 본 후기','<p>세상에 모든 재밌는 일은 내가 없을때 돌아간다...</p>','FREE'),(0,NULL,'2024-02-16 05:40:31.024798',25,20,'2024-02-16 05:40:31.024798','100억 받고 평생 백수하기 vs 전세계적으로 유명해지기','<p>클린스만은 다 가졌어...</p>','FREE'),(0,NULL,'2024-02-16 05:41:09.342439',26,23,'2024-02-16 05:41:09.342439','고백 거절하는거 진짜 빡세다..','<p>받는것부터가 어렵거든</p>','FREE'),(1,NULL,'2024-02-16 05:52:41.634817',27,23,'2024-02-16 09:46:22.997954','카페 같이갈사람..?','<p><span style=\"color: rgb(13, 13, 13);\">요즘 학교 앞에서 공부하는 카페가 엄청 유명하다는데, 가보고 싶은데 누가 같이 가줄까?</span></p>','FREE'),(0,NULL,'2024-02-16 05:53:09.095746',28,22,'2024-02-16 05:53:09.095746','방학계획 같이 짤사람?','<p><span style=\"color: rgb(13, 13, 13);\">오늘 방학 계획 짜봤는데, 너무 빡세게 짜면 지칠 것 같아. 적당한 여유도 필요하겠지?</span></p>','FREE'),(0,NULL,'2024-02-16 05:53:54.332063',29,19,'2024-02-16 05:53:54.332063','오점뭐?ㅊㅊ','<p><span style=\"color: rgb(13, 13, 13);\">오늘의 아침 메뉴가 너무 별로였어. </span></p><p><br></p><p><span style=\"color: rgb(13, 13, 13);\">점심 메뉴 추천좀!</span></p>','FREE'),(1,NULL,'2024-02-16 05:56:20.608547',30,20,'2024-02-16 05:57:31.476392','이번주 주말에 유기견 봉사활동 갈사람!','<p>파주쪽으로 가려고 하는데</p><p>생각보다 힘드니까</p><p>고민 많이 해보고 댓글 남겨줘!</p><p><br></p>','FREE'),(0,NULL,'2024-02-16 05:57:21.423901',31,21,'2024-02-16 05:57:21.423901','뭔가 미식축구 재밌어보이는데','<p>룰을 잘 모르겠네</p><p>혹시 룰 아는 사람 같이 볼 사람 있어?</p>','FREE'),(0,NULL,'2024-02-16 05:58:31.706945',32,23,'2024-02-16 05:58:31.706945','의대 증원 논란 많은데 이거 왜 논란이 되는거야?','<p>사람 없다없다 해서 늘려줬는데 왜 이렇게 논란이되는거야?</p>','FREE'),(0,NULL,'2024-02-16 05:59:07.067586',33,22,'2024-02-16 05:59:07.067586','아니 뭐했다고 벌써 2월 중순이야..?','<p>나 24년 된지 2주도 안된것같은데?</p>','FREE'),(0,NULL,'2024-02-16 06:05:18.192793',34,19,'2024-02-16 06:05:18.192793','축구 하고싶은데 여기는 다 온라인이라 쉽지가 않네...','<p>경기도 고양인데 너무 사람이 없고 매치도 없다 ㅠㅠ 같이 찰사람이 없나</p>','HOBBY'),(1,NULL,'2024-02-16 06:05:55.698539',35,20,'2024-02-16 09:08:54.391176','전자기기 좋아하는 사람들 있나?','<p>잇썹 보는사람 있어?</p><p><br></p><p>진짜 시간가는줄 모르겠어 ㅋㅋㅋ</p>','HOBBY'),(3,NULL,'2024-02-16 06:06:18.321748',36,20,'2024-02-16 09:09:07.121106','너무 진부하지만.. 롤 좋아하는사람?','<p>우리 사람 되면 같이 게임하면 좋겠는데 ㅎㅎㅎ</p>','HOBBY'),(0,NULL,'2024-02-16 06:07:03.774802',37,23,'2024-02-16 06:07:03.774802','맛집 투어 하는 사람 있던데','<p>그 글 어디갔지?</p><p>나는 떡볶이랑 마라탕 꼭 맛있는데 아니면 안먹는다 주의라</p><p>진짜 찐 맛집 아는사람들 같이가면 좋겠는데!</p>','FREE'),(4,NULL,'2024-02-16 06:58:26.799971',38,4,'2024-02-16 06:59:34.178125','안녕하세요 잘 부탁드립니다 ~ :)','<p>잘 부탁드립니다!</p>','FREE'),(0,NULL,'2024-02-16 09:10:58.062520',39,20,'2024-02-16 09:10:58.062520','독서모임할 사람있나요?','<p>너무 어려운건 저도 안좋아하고</p><p><br></p><p>좀 쉽고 흥미 위주 책으로 시작해서</p><p>어려운 책도 같이 도전해보자요!</p>','HOBBY'),(1,NULL,'2024-02-16 09:12:20.483893',40,21,'2024-02-16 09:12:41.838575','그림 그리는 거 좋아하는 사람은 없어?','<p>뭔가 예쁘고 멋있는거 그리는게 좋은데...</p><p>혹시 없나..?</p>','FREE'),(0,NULL,'2024-02-16 09:12:58.313635',42,21,'2024-02-16 09:12:58.313635','그림 그리는거 좋아하는 사람은 혹시 없어??','<p>뭔가 예쁘고 멋있는거 그리는게 좋은데...</p><p>혹시 없나..?</p>','HOBBY'),(0,NULL,'2024-02-16 09:14:11.746520',43,23,'2024-02-16 09:14:11.746520','캠핑 추천하고 싶은데!','<p>나는 캠핑 너무 좋아해서 2-3주에 한번씩 꼭 가!</p><p>다음주에도 가기로했어 ㅎㅎㅎ</p><p>너무 재밌겠다!</p><p>갔다오면 후기 남길게!</p>','HOBBY'),(1,NULL,'2024-02-16 09:15:59.790596',44,22,'2024-02-16 09:46:18.112624','스케이트보드 진짜 재밌어! 쉽고','<p>요즘에 트릭도 할 수 있고 다양하게 즐길수 있어!</p><p><span style=\"color: rgb(13, 13, 13);\">다양한 기술을 익히고 달리면 진짜 재밌어!</span></p><p><br></p><p>강남쪽 같이 가볼 사람 !!!</p><p>처음 타도 잘 가르쳐 줄게 ㅎㅎ</p>','FREE'),(0,NULL,'2024-02-16 09:17:40.442608',45,22,'2024-02-16 09:17:40.442608','난 엄마랑 너무 자주 싸워...','<p>학교 좀 제대로 다니면 안될까 해서 꾸짖으시는데</p><p>나도 잘 다니고 싶은데 잘 안되는걸 어떡하라구..</p>','COUNSELING'),(0,NULL,'2024-02-16 09:38:40.165687',46,1,'2024-02-16 09:38:40.165687','2024학년도 1학기 개강 안내','<h1><strong>안녕하세요, 학생 여러분. 2024학년도 1학기가 3월 2일에 개강합니다. </strong></h1><p><br></p><h3>수업은 대면과 비대면 혼합 모델로 진행될 예정입니다. 모든 학생들은 개강 전에 학교 지침을 확인하시고 준비해 주시기 바랍니다. 안전한 캠퍼스 환경 유지를 위해 코로나19 방역 지침을 철저히 준수해 주세요. 각 과목별 수업 방식과 시간표는 학교 웹사이트에서 확인 가능합니다.</h3>','NOTICE'),(1,NULL,'2024-02-16 09:38:58.426612',47,1,'2024-02-16 09:39:27.826389','도서관 이용 안내 업데이트','<p><span style=\"color: rgb(13, 13, 13);\">학생 및 교직원 여러분, 도서관 이용 규정이 업데이트 되었습니다. 새로운 규정에 따라, 도서관 방문 시 마스크 착용은 필수이며, 도서 대출 기간이 변경되었습니다. 이제부터는 대출 기간을 최대 4주까지 연장할 수 있습니다. 도서관 이용 시간과 자세한 변경 사항은 학교 도서관 웹페이지에서 확인하실 수 있습니다.</span></p>','NOTICE'),(1,NULL,'2024-02-16 09:39:25.370682',48,1,'2024-02-16 09:46:14.856342','학생 건강 검진 일정 안내','<p><span style=\"color: rgb(13, 13, 13);\">모든 재학생을 대상으로 하는 건강 검진이 4월 10일부터 4월 24일까지 실시됩니다. 학생 건강 검진은 학생들의 건강을 확인하고 관리하기 위한 중요한 프로그램입니다. 검진에는 신체 검사, 시력 검사, 청력 검사 등이 포함됩니다. 건강 검진 일정과 장소, 준비물 등 자세한 사항은 학교 보건소 웹사이트에서 확인하실 수 있습니다.</span></p>','NOTICE'),(0,NULL,'2024-02-16 09:41:17.279645',49,1,'2024-02-16 09:41:17.279645','환경 보호 캠페인 참여 안내','<p><span style=\"color: rgb(13, 13, 13);\">지속 가능한 삶을 위한 환경 보호 캠페인이 이번 달에 시작됩니다. 캠페인에는 쓰레기 줄이기, 재활용 촉진, 그린 캠퍼스 조성 등 다양한 활동이 포함됩니다. 학생, 교직원, 지역 사회 구성원 모두가 참여할 수 있으며, 자세한 일정과 참여 방법은 학교 환경위원회 웹페이지에서 확인할 수 있습니다.</span></p>','NOTICE'),(0,NULL,'2024-02-16 09:41:44.042692',50,1,'2024-02-16 09:41:44.042692','새 학기 동아리 모집 안내','<p><span style=\"color: rgb(13, 13, 13);\">새 학기를 맞이하여 다양한 학생 동아리에서 신입 회원을 모집합니다. 문화, 예술, 스포츠, 학술 등 다양한 분야의 동아리가 있으니, 관심 있는 학생들은 학교 동아리 연합회 웹사이트에서 모집 공고를 확인하고 지원해 주세요. 동아리 활동은 새로운 친구를 만나고, 취미와 관심사를 탐색하는 좋은 기회입니다.</span></p>','NOTICE'),(0,NULL,'2024-02-16 09:42:38.756220',51,1,'2024-02-16 09:42:38.756220','온라인 학습 자료실 업데이트 안내','<p><span style=\"color: rgb(13, 13, 13);\">학생들의 원활한 학습을 지원하기 위해 온라인 학습 자료실이 새롭게 업데이트 되었습니다. 최신 교과서, 참고자료, 강의 노트 등 다양한 학습 자료가 추가되었으니, 학습 자료실을 적극 활용해 주세요. 접속 방법과 이용 안내는 학교 홈페이지에서 확인할 수 있습니다.</span></p>','NOTICE'),(0,NULL,'2024-02-16 09:43:11.355893',52,1,'2024-02-16 09:43:11.355893','교내 영어 말하기 대회 개최','<p><span style=\"color: rgb(13, 13, 13);\">영어 사용 능력을 향상시키고자 하는 학생들을 위한 영어 말하기 대회가 다음 달에 개최됩니다. 주제는 \'내가 꿈꾸는 미래\'로, 참가를 희망하는 학생은 사전에 신청해야 합니다. 대회 관련 자세한 정보와 신청 방법은 영어학과 웹페이지에서 확인 가능합니다.</span></p>','NOTICE'),(0,NULL,'2024-02-16 09:43:25.481129',53,1,'2024-02-16 09:43:25.481129','학생 복지 프로그램 소개','<p><span style=\"color: rgb(13, 13, 13);\">학교에서는 학생들의 건강하고 행복한 학교 생활을 지원하기 위해 다양한 학생 복지 프로그램을 운영하고 있습니다. 멘토링, 상담 서비스, 건강 검진 등 다양한 서비스를 제공하니, 필요한 학생들은 학생 복지부를 방문하여 상담받으세요.</span></p>','NOTICE'),(0,NULL,'2024-02-16 09:44:06.097507',54,1,'2024-02-16 09:44:06.097507','학기 중 스트레스 관리 세미나 개최','<p><span style=\"color: rgb(13, 13, 13);\">학기 중 받는 스트레스를 효과적으로 관리할 수 있는 방법을 배울 수 있는 세미나가 개최됩니다. 전문가가 진행하는 이 세미나는 학업 성취도 향상과 개인의 웰빙을 위해 매우 유익합니다. 세미나 일정과 참여 방법은 학교 웹사이트에서 확인할 수 있습니다.</span></p>','NOTICE'),(1,NULL,'2024-02-16 09:44:24.370383',55,1,'2024-02-16 09:58:25.244810','교내 봉사 활동 프로그램 모집','<p><span style=\"color: rgb(13, 13, 13);\">학교에서는 지역 사회에 기여하고자 하는 학생들을 위해 봉사 활동 프로그램을 운영합니다. 다양한 봉사 활동 기회가 제공되므로, 관심 있는 학생들은 학생 봉사 프로그램 담당자에게 문의하여 신청하세요.</span></p>','NOTICE'),(0,NULL,'2024-02-16 09:46:31.674849',56,2,'2024-02-16 09:46:31.674849','친구들과 잘 지내고 싶어요','<p><span style=\"color: rgb(13, 13, 13);\">요즘 친구들과 어울리기가 어려워졌어요. 같이 놀 때도 자주 소외감을 느끼고, 대화에 끼기도 힘들어요. 어떻게 하면 자연스럽게 친구들과 어울릴 수 있을까요?</span></p>','FREE'),(0,NULL,'2024-02-16 09:47:06.847642',57,2,'2024-02-16 09:47:06.847642','시간 관리가 잘 안돼요','<p><span style=\"color: rgb(13, 13, 13);\">학교 공부, 방과 후 활동, 취미 생활까지 하려니 시간이 부족해요. 모든 것을 잘 하고 싶은데, 시간 관리를 어떻게 효율적으로 할 수 있을까요? 늘 시간에 쫓기는 느낌이 들어서 스트레스를 많이 받아요.</span></p>','COUNSELING'),(0,NULL,'2024-02-16 09:48:32.220783',58,2,'2024-02-16 09:48:32.220783','코딩으로 만드는 나만의 세상','<p><span style=\"color: rgb(13, 13, 13);\"> 파이썬과 자바스크립트로 간단한 프로젝트를 해보고 있는데, 이런 취미를 가진 친구들과 정보를 공유하고 싶어요. 함께 학습하고, 프로젝트 아이디어를 나눌 수 있는 스터디 그룹을 만들고 싶습니다.</span></p>','HOBBY');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_comment`
--

DROP TABLE IF EXISTS `article_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_comment` (
  `article_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `modified_at` datetime(6) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKghmocqkgqs5tkmucf5putw64t` (`article_id`),
  KEY `FKk9vyararhec6o9v70qg1pjmo3` (`member_id`),
  CONSTRAINT `FKghmocqkgqs5tkmucf5putw64t` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `FKk9vyararhec6o9v70qg1pjmo3` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_comment`
--

LOCK TABLES `article_comment` WRITE;
/*!40000 ALTER TABLE `article_comment` DISABLE KEYS */;
INSERT INTO `article_comment` VALUES (1,'2024-02-16 03:22:13.371093',1,11,'2024-02-16 03:22:13.371093','감사합니다 잘 이용해보겠습니다'),(1,'2024-02-16 03:25:38.566781',2,12,'2024-02-16 03:25:38.566781','우와 너무 기대되요!'),(3,'2024-02-16 03:26:03.385137',3,12,'2024-02-16 03:26:03.385137','저는 문제라고 생각해요!! '),(2,'2024-02-16 03:28:21.370458',4,12,'2024-02-16 03:28:21.370458','안녕하세요~~'),(3,'2024-02-16 03:31:25.177766',5,13,'2024-02-16 03:31:25.177766','당연히 되죠 밥 빨리 먹는게 더 중요해요'),(5,'2024-02-16 03:31:58.659366',6,13,'2024-02-16 03:31:58.659366','용기내서 고백해보세요! 두렵다고 고백하지 않으면 나중에 속상하지 않을까요?'),(4,'2024-02-16 03:32:24.485506',7,13,'2024-02-16 03:32:24.485506','저는 뮌헨 팬이예요~'),(14,'2024-02-16 03:51:07.935967',8,16,'2024-02-16 03:51:07.935967','이건 좀 아닌 것 같습니다... 손이 없나요 발이 없나요 ?!'),(17,'2024-02-16 03:56:32.446687',9,12,'2024-02-16 03:56:32.446687','난 좋다고 생각해 '),(17,'2024-02-16 03:56:37.598761',10,12,'2024-02-16 03:56:37.598761','전소연은 천재야'),(4,'2024-02-16 03:57:00.129299',11,7,'2024-02-16 03:57:00.129299','맨시티 팬입니당 연락주세요'),(15,'2024-02-16 03:57:51.047817',12,12,'2024-02-16 03:57:51.047817','그거 엄청 비싼거 알지..?'),(13,'2024-02-16 03:58:06.855886',13,12,'2024-02-16 03:58:06.855886','감사합니다 다음번에 한번 가볼게요!'),(8,'2024-02-16 03:58:27.753508',14,12,'2024-02-16 03:58:27.753508','역시 우리오빠'),(6,'2024-02-16 03:58:38.097580',15,12,'2024-02-16 03:58:38.097580','나는 INTP야'),(2,'2024-02-16 09:45:52.225433',16,25,'2024-02-16 09:45:52.225433','반가워요!!');
/*!40000 ALTER TABLE `article_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `status` tinyint NOT NULL,
  `check_in` datetime(6) DEFAULT NULL,
  `check_out` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKslaf4mu3eu0gi72u4t9xcsxjd` (`member_id`),
  CONSTRAINT `FKslaf4mu3eu0gi72u4t9xcsxjd` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `attendance_chk_1` CHECK ((`status` between 0 and 4))
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (0,'2024-02-01 09:48:34.000000','2024-02-01 18:48:34.000000',1,4),(1,'2024-02-02 09:48:34.000000','2024-02-02 18:48:34.000000',2,4),(1,'2024-02-03 09:48:34.000000','2024-02-03 18:48:34.000000',3,4),(2,'2024-02-04 09:48:34.000000','2024-02-04 18:48:34.000000',4,4),(2,'2024-02-05 09:48:34.000000','2024-02-05 18:48:34.000000',5,4),(0,'2024-02-06 09:48:34.000000','2024-02-06 18:48:34.000000',6,4),(0,'2024-02-07 09:48:34.000000','2024-02-07 18:48:34.000000',7,4),(0,'2024-02-08 09:48:34.000000','2024-02-08 18:48:34.000000',8,4),(0,'2024-02-09 09:48:34.000000','2024-02-09 18:48:34.000000',9,4),(3,'2024-02-10 09:48:34.000000','2024-02-10 18:48:34.000000',10,4),(3,'2024-02-11 09:48:34.000000','2024-02-11 18:48:34.000000',11,4),(3,'2024-02-16 09:50:01.609778','2024-02-16 09:50:12.495367',12,4),(1,'2024-02-16 09:51:25.016487',NULL,13,2),(1,'2024-02-16 09:54:34.561572',NULL,14,11),(1,'2024-02-16 09:55:11.927395',NULL,15,25),(1,'2024-02-16 09:57:08.987204',NULL,16,6);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `badge`
--

DROP TABLE IF EXISTS `badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `badge` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `badge`
--

LOCK TABLES `badge` WRITE;
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` VALUES (1,'https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/e1393253-70bb-43df-91c0-eb08cf940141.png'),(2,'https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/38ae118f-ca62-45a6-b591-376bbce43bdc.png'),(3,'https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/ec37c826-aece-46ff-8372-775b1104c4e2.png'),(4,'https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/484b859a-413a-42aa-98bb-50e7d51c4508.png'),(5,'https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/681b5e23-b73a-4441-8b40-2853d52dbede.png');
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `chat_room_id` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `chat_message_type` enum('MESSAGE','SUBSCRIBE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj52yap2xrm9u0721dct0tjor9` (`chat_room_id`),
  KEY `FKynfbnbqot8mpd1tquoc2s1w5` (`member_id`),
  CONSTRAINT `FKj52yap2xrm9u0721dct0tjor9` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`id`),
  CONSTRAINT `FKynfbnbqot8mpd1tquoc2s1w5` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
INSERT INTO `chat_message` VALUES ('2024-02-16 03:04:11.270458',1,7,'f79bda26-360d-4de5-9fea-cf61ad00c29b','하이','MESSAGE'),('2024-02-16 03:04:12.443357',2,7,'f79bda26-360d-4de5-9fea-cf61ad00c29b','ㅏㅎ면','MESSAGE'),('2024-02-16 03:04:17.199445',3,7,'f79bda26-360d-4de5-9fea-cf61ad00c29b','시간이 안남는구나','MESSAGE'),('2024-02-16 03:04:34.732066',4,11,'dca0c304-b3f7-4c70-b70b-09718b071dbd','ㅎㅇ','MESSAGE'),('2024-02-16 03:04:35.774417',5,11,'dca0c304-b3f7-4c70-b70b-09718b071dbd','안녕','MESSAGE'),('2024-02-16 03:04:37.988637',6,11,'dca0c304-b3f7-4c70-b70b-09718b071dbd','ㅋㅋ','MESSAGE'),('2024-02-16 03:46:30.056051',7,2,'db72f01f-b0a5-4aa6-a08d-7f4bcc4f0367','졸립다','MESSAGE'),('2024-02-16 09:05:25.949366',8,4,'c9cba9a1-5d71-4be2-8d42-8980bfe57053','바훈투르 먹음?','MESSAGE'),('2024-02-16 09:05:58.435601',9,2,'c9cba9a1-5d71-4be2-8d42-8980bfe57053','오늘 하루도 기대가 된다!','MESSAGE'),('2024-02-16 09:08:21.169735',10,3,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b','좋은 아침이야!','MESSAGE'),('2024-02-16 09:08:29.911832',11,4,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b','오늘도 좋은 하루 !','MESSAGE'),('2024-02-16 09:08:38.508898',12,3,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b','반가워!','MESSAGE'),('2024-02-16 09:08:41.338258',13,4,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b','오늘 밥뭐야?','MESSAGE'),('2024-02-16 09:08:43.938760',14,3,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b','오늘 하루도 화이팅!','MESSAGE'),('2024-02-16 09:08:47.870974',15,3,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b','오늘은 제육덮밥이야','MESSAGE'),('2024-02-16 09:08:52.619464',16,4,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b','안전 좋아좋아좋아','MESSAGE'),('2024-02-16 09:08:55.649265',17,3,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b','좋아!','MESSAGE');
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `last_chat_message_id` bigint DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r9igrbwakr14wb1ihl7sd63rk` (`last_chat_message_id`),
  CONSTRAINT `FK3e410j9huednb42o10v12b2oh` FOREIGN KEY (`last_chat_message_id`) REFERENCES `chat_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (3,'f79bda26-360d-4de5-9fea-cf61ad00c29b'),(6,'dca0c304-b3f7-4c70-b70b-09718b071dbd'),(7,'db72f01f-b0a5-4aa6-a08d-7f4bcc4f0367'),(9,'c9cba9a1-5d71-4be2-8d42-8980bfe57053'),(17,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b');
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room_member`
--

DROP TABLE IF EXISTS `chat_room_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `chat_room_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo6a9v51aal2574fjb1ldlw4di` (`chat_room_id`),
  KEY `FKq64atn9y4cyjpp4qcrllxi3o5` (`member_id`),
  CONSTRAINT `FKo6a9v51aal2574fjb1ldlw4di` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`id`),
  CONSTRAINT `FKq64atn9y4cyjpp4qcrllxi3o5` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room_member`
--

LOCK TABLES `chat_room_member` WRITE;
/*!40000 ALTER TABLE `chat_room_member` DISABLE KEYS */;
INSERT INTO `chat_room_member` VALUES (1,7,'f79bda26-360d-4de5-9fea-cf61ad00c29b'),(2,5,'f79bda26-360d-4de5-9fea-cf61ad00c29b'),(3,11,'dca0c304-b3f7-4c70-b70b-09718b071dbd'),(4,15,'dca0c304-b3f7-4c70-b70b-09718b071dbd'),(5,2,'db72f01f-b0a5-4aa6-a08d-7f4bcc4f0367'),(6,5,'db72f01f-b0a5-4aa6-a08d-7f4bcc4f0367'),(7,4,'c9cba9a1-5d71-4be2-8d42-8980bfe57053'),(8,2,'c9cba9a1-5d71-4be2-8d42-8980bfe57053'),(9,3,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b'),(10,4,'71e2d4b5-3728-4e98-b65d-81e3c6b9f06b');
/*!40000 ALTER TABLE `chat_room_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classroom`
--

DROP TABLE IF EXISTS `classroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classroom` (
  `grade` int NOT NULL,
  `number` int NOT NULL,
  `year` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classroom`
--

LOCK TABLES `classroom` WRITE;
/*!40000 ALTER TABLE `classroom` DISABLE KEYS */;
INSERT INTO `classroom` VALUES (1,1,2024,1,NULL),(1,2,2024,2,NULL),(1,3,2024,3,NULL),(1,4,2024,4,NULL),(1,5,2024,5,NULL),(2,1,2024,6,NULL),(2,2,2024,7,NULL),(2,3,2024,8,NULL),(2,4,2024,9,NULL),(2,5,2024,10,NULL),(3,1,2024,11,NULL),(3,2,2024,12,NULL),(3,3,2024,13,NULL),(3,4,2024,14,NULL),(3,5,2024,15,NULL),(4,1,2024,16,NULL),(4,2,2024,17,NULL),(4,3,2024,18,'https://our-tiny-classroom.daily.co/2024-4-3'),(4,4,2024,19,NULL),(4,5,2024,20,NULL),(5,1,2024,21,NULL),(5,2,2024,22,NULL),(5,3,2024,23,NULL),(5,4,2024,24,NULL),(5,5,2024,25,NULL),(6,1,2024,26,NULL),(6,2,2024,27,NULL),(6,3,2024,28,NULL),(6,4,2024,29,NULL),(6,5,2024,30,NULL),(7,1,2024,31,NULL),(7,2,2024,32,NULL),(7,3,2024,33,NULL),(7,4,2024,34,NULL),(7,5,2024,35,NULL),(8,1,2024,36,NULL),(8,2,2024,37,NULL),(8,3,2024,38,NULL),(8,4,2024,39,NULL),(8,5,2024,40,NULL),(9,1,2024,41,NULL),(9,2,2024,42,NULL),(9,3,2024,43,NULL),(9,4,2024,44,NULL),(9,5,2024,45,NULL),(10,1,2024,46,NULL),(10,2,2024,47,NULL),(10,3,2024,48,NULL),(10,4,2024,49,NULL),(10,5,2024,50,NULL),(11,1,2024,51,NULL),(11,2,2024,52,NULL),(11,3,2024,53,NULL),(11,4,2024,54,NULL),(11,5,2024,55,NULL),(12,1,2024,56,NULL),(12,2,2024,57,NULL),(12,3,2024,58,NULL),(12,4,2024,59,NULL),(12,5,2024,60,NULL),(1,0,2024,61,NULL),(2,0,2024,62,NULL),(3,0,2024,63,NULL),(4,0,2024,64,NULL),(5,0,2024,65,NULL),(6,0,2024,66,NULL),(7,0,2024,67,NULL),(8,0,2024,68,NULL),(9,0,2024,69,NULL),(10,0,2024,70,NULL),(11,0,2024,71,NULL),(12,0,2024,72,NULL);
/*!40000 ALTER TABLE `classroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goal`
--

DROP TABLE IF EXISTS `goal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goal` (
  `achievement` int NOT NULL,
  `week` int NOT NULL,
  `year` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK62jdhr16mniv5cf7pd2gtc79y` (`member_id`),
  CONSTRAINT `FK62jdhr16mniv5cf7pd2gtc79y` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goal`
--

LOCK TABLES `goal` WRITE;
/*!40000 ALTER TABLE `goal` DISABLE KEYS */;
INSERT INTO `goal` VALUES (0,7,2024,1,7,'꼭 제대로..!'),(0,7,2024,2,11,'싸탈 대신 해탈'),(0,7,2024,3,4,'일주일에 네 번 이상 운동하기');
/*!40000 ALTER TABLE `goal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `heart`
--

DROP TABLE IF EXISTS `heart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `heart` (
  `article_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `modified_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk9abrl295vgpks5xhg6bhx7i9` (`article_id`),
  KEY `FKiqbtbunbl2h0r928gnlg7ncta` (`member_id`),
  CONSTRAINT `FKiqbtbunbl2h0r928gnlg7ncta` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKk9abrl295vgpks5xhg6bhx7i9` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `heart`
--

LOCK TABLES `heart` WRITE;
/*!40000 ALTER TABLE `heart` DISABLE KEYS */;
/*!40000 ALTER TABLE `heart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecture`
--

DROP TABLE IF EXISTS `lecture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecture` (
  `date` date DEFAULT NULL,
  `day_of_week` int NOT NULL,
  `time_table` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `lecture_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `approved` enum('APPROVED','WAITING','REJECTED') DEFAULT NULL,
  `lecture_category_type` enum('NO_CATEGORY','KOREAN','MATH','SCIENCE','ENGLISH','MUSIC','ART','ETHICS','HOME_ECONOMICS','CAREER','HOBBY') NOT NULL,
  `lecture_type` enum('FREE_LECTURE','REGULAR_LECTURE','SPECIAL_LECTURE') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKay9pfbiei970bcn4c8drx4byo` (`member_id`),
  CONSTRAINT `FKay9pfbiei970bcn4c8drx4byo` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecture`
--

LOCK TABLES `lecture` WRITE;
/*!40000 ALTER TABLE `lecture` DISABLE KEYS */;
INSERT INTO `lecture` VALUES (NULL,0,0,1,1,'모션 그래픽 공부',NULL,'모션 그래픽','APPROVED','ART','FREE_LECTURE'),(NULL,0,0,2,1,'꽃꽃꽃꽃',NULL,'꽃꽂이','APPROVED','ART','FREE_LECTURE'),(NULL,0,0,3,1,'네일아트 공부',NULL,'네일아트','APPROVED','ART','FREE_LECTURE'),(NULL,0,0,4,1,'피아노피아노',NULL,'피아노','APPROVED','MUSIC','FREE_LECTURE'),(NULL,0,0,5,1,'클래식 감상',NULL,'고전 음악의 이해','APPROVED','MUSIC','FREE_LECTURE'),(NULL,0,0,6,1,'한시간동안 기타 연습',NULL,'기타 연주','APPROVED','MUSIC','FREE_LECTURE'),(NULL,0,0,7,1,'백준에서 실버 한문제 풀기',NULL,'알고리즘','APPROVED','MATH','FREE_LECTURE'),(NULL,0,0,8,1,'미분과 적분 개념정리',NULL,'미적분학','APPROVED','MATH','FREE_LECTURE'),(NULL,0,0,9,1,'체스를 같이 할 친구가 있으신가요?',NULL,'체스','APPROVED','HOBBY','FREE_LECTURE'),(NULL,0,0,10,1,'장기를 같이 할 친구가 있으신가요?',NULL,'장기','APPROVED','HOBBY','FREE_LECTURE'),(NULL,0,0,11,1,'수학수학',NULL,'수학','APPROVED','MATH','REGULAR_LECTURE'),(NULL,0,0,12,1,'영어영어',NULL,'영어','APPROVED','ENGLISH','REGULAR_LECTURE'),(NULL,0,0,13,1,'도덕도덕',NULL,'도덕','APPROVED','ETHICS','REGULAR_LECTURE'),(NULL,0,0,14,1,'국어',NULL,'고전문학','APPROVED','KOREAN','REGULAR_LECTURE'),('2024-02-15',3,5,15,9,'갈등 해결을 위한 대화법',NULL,'갈등해결을 위한 대화기술','APPROVED','NO_CATEGORY','SPECIAL_LECTURE'),('2024-02-16',4,5,16,10,'신뢰를 얻는 대화법',NULL,'대화의 심리학: 소통에서 신뢰로','APPROVED','NO_CATEGORY','SPECIAL_LECTURE'),('2024-04-14',0,2,17,16,'이번 특강에서는 자신에게 가장 잘 어울리는 색상을 찾아보는 퍼스널 컬러 진단과 활용 방법을 배울 수 있습니다. 전문 컬러리스트의 안내로 본인만의 스타일을 찾아 개성을 표현하는 방법을 알아보세요. 특강은 4월 15일에 진행되며, 사전 신청자에 한해 참여가 가능합니다.','https://our-tiny-classroom.daily.co/a6af67c8-4566-4bba-ac00-a6c7e87b09ce','퍼스널 컬러 기초','APPROVED','MATH','SPECIAL_LECTURE'),('2024-02-18',0,3,18,16,'이 특강은 중학생들이 일상에서 실천할 수 있는 건강한 생활 습관을 배우는 데 초점을 맞춥니다. ','https://our-tiny-classroom.daily.co/61053de5-4e9f-408d-af59-d2dfd986cbaf','중학생을 위한 건강한 삶의 습관 특강','APPROVED','SCIENCE','SPECIAL_LECTURE'),('2024-02-18',0,2,19,18,'여러번 들어는 봤지만, 도대체 뭔지 모르겠는 과학 속 개념들!\n드디어 궤도가 어떻게든 익숙하게 만들기 위해 준비했습니다 🙂','https://our-tiny-classroom.daily.co/49841212-0db9-4509-86ac-9662b1f59bd2','궤도의 열역학개론 ','APPROVED','KOREAN','SPECIAL_LECTURE'),('2024-02-18',0,5,20,16,'요즘 삼성 알고리즘 B형 특강 하나면 찬 겨울같은 채용시장도 무섭지 않다죠? 이번 기회에 한번 알아봅시다!','https://our-tiny-classroom.daily.co/8cd6eb2a-a411-4352-ae41-9e27e05c9334','알고리즘 B형 특강','APPROVED','NO_CATEGORY','SPECIAL_LECTURE'),('2024-02-16',0,0,21,16,'우울증이 의심되는 학생이 있다면 꼭 신청 부탁드립니다.','https://our-tiny-classroom.daily.co/b46ffe6b-4e5e-4f0b-9bc5-6f87bf44937a','우울증, 마음의 감기','APPROVED','MUSIC','SPECIAL_LECTURE'),('2024-02-16',0,0,22,16,'일차 방정식은, 앞으로 배워나갈 수학의 기초라고 볼 수 있습니다. 차근차근 친해져보아요!','https://our-tiny-classroom.daily.co/d64fe69f-7c27-4d7e-ab78-875895899c95','일차 방정식 특강','APPROVED','NO_CATEGORY','SPECIAL_LECTURE'),('2024-02-16',0,2,23,18,'상대성 이론이란 무엇인가? 일반 상대성 이론? 특수 상대성 이론?\n도대체 왜 필요했는지 정말 궁금한 특수 상대성 이론의 시작!','https://our-tiny-classroom.daily.co/748bf6c6-9aa1-4f17-bc9f-7935b645d2ef','궤도의 상대성 이론 1탄','APPROVED','KOREAN','SPECIAL_LECTURE');
/*!40000 ALTER TABLE `lecture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `birthday` date DEFAULT NULL,
  `point` int DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `member_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profile_url` varchar(255) DEFAULT NULL,
  `role` enum('ROLE_USER','ROLE_STUDENT','ROLE_TEACHER','ROLE_MENTOR','ROLE_ADMIN','ROLE_WAITING') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4rw879c4q7wrgi3v64cy73b17` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('2023-09-08',53,NULL,1,'unabbs1@sfgate.com','admin','관리자','$2a$10$tJSm6EfLNxha1.8CrAnJIOeaeC7oUo518GYPJKZiC3rx3rjg41KJa',NULL,'ROLE_ADMIN'),('1999-04-04',128,NULL,2,'tjgml1@naver.com','tjgml1 ','정서희','$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.','https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg','ROLE_STUDENT'),('1996-05-04',65,NULL,3,'rmsdud1@naver.com','rmsdud1','최근영','$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.','https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg','ROLE_STUDENT'),('1998-02-16',888,NULL,4,'dudqls1@naver.com','dudqls1','전영빈','$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.','https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/b78631e5-acb9-4343-bcbf-454bc9d4e8c1.jpg','ROLE_STUDENT'),('1998-11-10',751,NULL,5,'wotjd1@naver.com','wotjd1','장재성','$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.','https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg','ROLE_STUDENT'),('1995-09-04',654,NULL,6,'wotjs1@naver.com','wotjs1','박재선','$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.','https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg','ROLE_STUDENT'),('1994-06-04',456,NULL,7,'ckdgh1@naver.com','ckdgh1','최창호','$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.','https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg','ROLE_STUDENT'),('2023-09-08',53,NULL,8,'unabbs1@sfgate.com','teacher','김대성','$2a$10$tJSm6EfLNxha1.8CrAnJIOeaeC7oUo518GYPJKZiC3rx3rjg41KJa',NULL,'ROLE_TEACHER'),('2023-09-08',53,NULL,9,'unabbs1@sfgate.com','mentor1','장대성','$2a$10$tJSm6EfLNxha1.8CrAnJIOeaeC7oUo518GYPJKZiC3rx3rjg41KJa',NULL,'ROLE_MENTOR'),('2023-09-08',53,NULL,10,'unabbs1@sfgate.com','mentor2','이병옥','$2a$10$tJSm6EfLNxha1.8CrAnJIOeaeC7oUo518GYPJKZiC3rx3rjg41KJa',NULL,'ROLE_MENTOR'),('1999-09-09',0,NULL,11,'abc@abc.com','abcd1','김덕배','$2a$10$ZcPxysswubOo2krSHQfLUepNnWr8lgt4wQ4x6yf4CcSeT8ORVVKR2',NULL,'ROLE_STUDENT'),('2000-10-10',0,NULL,12,'abcd@abc.com','abcd2','박지은','$2a$10$bsRewnWn3t2xHpd5UgiqlePxZUEfLGxj9mdW3tkBfQI/k17Q7ANK.',NULL,'ROLE_STUDENT'),('2003-12-02',0,NULL,13,'abc@abcd.com','abcd3','조준용','$2a$10$vWiyXPylLaQ6GRf6f6s2Z.i2Kvi7Lgwz7Mj1I0O9sJ8xEMGrFUhL.',NULL,'ROLE_STUDENT'),('2004-03-12',0,NULL,14,'abcd@abcd.com','abcd4','김은주','$2a$10$7U8AQtOOHSUzXoqNF8DDBOKo.BYLdN.H6kGNiB2V6G7SXFpVeZupO',NULL,'ROLE_STUDENT'),('2004-06-09',0,NULL,15,'abcde@abce.com','abcd5','박주영','$2a$10$l6e4Hpwmm9Cs3FmPxu6Oqu5/9GCn.wM2g6fKaPXBQjY5dqXzLyW7.',NULL,'ROLE_STUDENT'),('1998-03-02',0,NULL,16,'tjddlf@ssafy.com','mentor3','윤성일','$2a$10$OW2pE5O6lzyFoaIucIxzmeVdZET5iDd97uEk7JVAOaxPtA9lvTfau',NULL,'ROLE_MENTOR'),('2024-02-21',0,NULL,17,'hch912@nave.com','chh9450','최창호','$2a$10$9x08.OBAI.4Ir55LC4qQ5OLDNdAbTowSC1TOHiyq4HD9cbQF.tFW6',NULL,'ROLE_MENTOR'),('1983-08-11',0,NULL,18,'wogur@gmail.com','mentor4','김재혁','$2a$10$OqTiCMrlD.kDWUeagjCi.O.qtzppJPt4GytqINvtyTg9qIqCiV3Pu',NULL,'ROLE_MENTOR'),('2024-02-08',0,NULL,19,'asdf1@naver.com','asdf1','유회승','$2a$10$aZvnC13l4LQve6xlJsfjbO4YcnRJMA0nqtWMRflYoiKfbcvpbXlnu',NULL,'ROLE_STUDENT'),('2008-07-16',0,NULL,20,'asdf2@naver.com','asdf2','이유리','$2a$10$UclOQzWNRXC9s2KTdiFcduI9srsPANTpRMy9t602CEvrwkvl/VMju',NULL,'ROLE_STUDENT'),('2008-10-30',0,NULL,21,'asdf3@nate.com','asdf3','김동선','$2a$10$6A7G9aximoB2qflVr7UfdOl3usHYLWcNO3icBIF/fZHzwBGOkSBmm',NULL,'ROLE_STUDENT'),('2008-06-30',0,NULL,22,'asdf4@nav.com','asdf4','김도현','$2a$10$kB.BXYNFnEif.2ovx1stReA/xoWRDetTYpljtdy2zMrr8W2KM4dPa','https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/c1551e7c-7e98-4cc6-8ef9-b44aaba24d37.png','ROLE_STUDENT'),('2024-02-22',0,NULL,23,'asdf5@nate.com','asdf5','박상원','$2a$10$zgYUD0UPD7CkR7ydZAXUw.5KtW1YjfQZgYSrtd4Nhwgno194PT1Wu',NULL,'ROLE_STUDENT'),('2004-11-16',0,NULL,24,'asdf6@nate.com','asdf6','이대웅','$2a$10$R8Byi2RdDUG14AdwQ2V5hu4ZA1OoOUdUq.6eg3Gu6C.6I8O0uRdNu',NULL,'ROLE_STUDENT'),('1996-06-17',0,NULL,25,'wwaall2626@naver.com','ought119','최근영','$2a$10$/bRg5fAFZToxWqubYAu4JOK2xfxWSmpZd3mLRy9GxwymGx1gukcWy',NULL,'ROLE_STUDENT'),('2002-05-05',0,NULL,26,'abc@ab.com','abcd6','곽주형','$2a$10$dQTlbpvSJItleEdeqE30c.3LCBbOAzX7v/HdNcu5kZWSE9cBj1YqW',NULL,'ROLE_USER'),('2002-03-14',0,NULL,27,'abc@abc.com','abcd7','손정민','$2a$10$8lX1FuFIt1NLfQ1w/EPzluaiG3uFZ4fiSvYBEd3Zwtvn/N.NjPHLC',NULL,'ROLE_WAITING');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_badge`
--

DROP TABLE IF EXISTS `member_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_badge` (
  `badge_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK39altb7wsyx1gyx3m492wf2kb` (`badge_id`),
  KEY `FKc9u2tetajy4dohtwi9dnpas07` (`member_id`),
  CONSTRAINT `FK39altb7wsyx1gyx3m492wf2kb` FOREIGN KEY (`badge_id`) REFERENCES `badge` (`id`),
  CONSTRAINT `FKc9u2tetajy4dohtwi9dnpas07` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_badge`
--

LOCK TABLES `member_badge` WRITE;
/*!40000 ALTER TABLE `member_badge` DISABLE KEYS */;
INSERT INTO `member_badge` VALUES (1,1,4),(2,2,4),(3,3,16),(3,4,11),(3,5,12),(3,6,13),(3,7,14),(3,8,15),(3,9,17),(3,10,18),(3,11,19),(3,12,20),(3,13,22),(3,14,21),(3,15,23),(3,16,24),(3,17,25);
/*!40000 ALTER TABLE `member_badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_class_room`
--

DROP TABLE IF EXISTS `member_class_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_class_room` (
  `class_room_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk5ysqqrokpgs40566q4udl4qa` (`class_room_id`),
  KEY `FKar2cksxj9rkibb9m3ij2rqg4e` (`member_id`),
  CONSTRAINT `FKar2cksxj9rkibb9m3ij2rqg4e` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKk5ysqqrokpgs40566q4udl4qa` FOREIGN KEY (`class_room_id`) REFERENCES `classroom` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_class_room`
--

LOCK TABLES `member_class_room` WRITE;
/*!40000 ALTER TABLE `member_class_room` DISABLE KEYS */;
INSERT INTO `member_class_room` VALUES (18,1,2),(18,2,3),(18,3,4),(18,4,5),(18,5,6),(18,6,7),(18,7,8),(18,8,11),(18,9,12),(18,10,13),(18,11,14),(18,12,15),(18,13,19),(18,14,20),(18,15,22),(18,16,21),(18,17,23),(18,18,24),(5,19,25),(18,20,25);
/*!40000 ALTER TABLE `member_class_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_schedule`
--

DROP TABLE IF EXISTS `member_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `schedule_id` bigint DEFAULT NULL,
  `lecture_category_type` enum('NO_CATEGORY','KOREAN','MATH','SCIENCE','ENGLISH','MUSIC','ART','ETHICS','HOME_ECONOMICS','CAREER','HOBBY') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjmxjh6tt9uegmkeb6kqn7xoj5` (`member_id`),
  KEY `FK85h38v1n4lgwdt3xi0mfujew3` (`schedule_id`),
  CONSTRAINT `FK85h38v1n4lgwdt3xi0mfujew3` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`),
  CONSTRAINT `FKjmxjh6tt9uegmkeb6kqn7xoj5` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_schedule`
--

LOCK TABLES `member_schedule` WRITE;
/*!40000 ALTER TABLE `member_schedule` DISABLE KEYS */;
INSERT INTO `member_schedule` VALUES (1,4,1,'MATH'),(2,4,2,'ENGLISH'),(3,4,3,'ETHICS'),(4,4,4,'KOREAN'),(5,7,6,'HOBBY'),(6,7,7,'MUSIC'),(7,7,8,'MATH'),(8,7,9,'MUSIC'),(9,7,10,'MUSIC'),(10,7,11,'NO_CATEGORY'),(18,8,19,'KOREAN'),(21,8,22,'KOREAN'),(27,4,28,'NO_CATEGORY'),(28,4,29,'HOBBY'),(29,3,30,'NO_CATEGORY'),(30,3,31,'MATH');
/*!40000 ALTER TABLE `member_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mentor_role_update_request`
--

DROP TABLE IF EXISTS `mentor_role_update_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mentor_role_update_request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `belong` varchar(255) DEFAULT NULL,
  `belong_document_paths` varchar(255) DEFAULT NULL,
  `original_file_names` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_onfxouwpmabioudulaydg1too` (`member_id`),
  CONSTRAINT `FK1f9tvypxi6523tvaij2qyglck` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mentor_role_update_request`
--

LOCK TABLES `mentor_role_update_request` WRITE;
/*!40000 ALTER TABLE `mentor_role_update_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `mentor_role_update_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `day_of_week` int NOT NULL,
  `deletable` bit(1) NOT NULL,
  `schedule_date` date NOT NULL,
  `time_table` int NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lecture_id` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdhqdej1p3232l393s9b6i6924` (`lecture_id`),
  KEY `FKn7js9p799qcts7le20pec9bxr` (`member_id`),
  CONSTRAINT `FKdhqdej1p3232l393s9b6i6924` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`),
  CONSTRAINT `FKn7js9p799qcts7le20pec9bxr` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (2,_binary '\0','2024-02-14',3,NULL,1,12,8),(2,_binary '\0','2024-02-14',4,NULL,2,11,8),(1,_binary '\0','2024-02-13',3,NULL,3,13,8),(1,_binary '\0','2024-02-13',4,NULL,4,14,8),(3,_binary '\0','2024-02-15',5,'2024-02-16 03:11:42.419584',5,15,8),(0,_binary '','2024-02-12',0,NULL,6,9,7),(0,_binary '','2024-02-12',1,NULL,7,4,7),(0,_binary '','2024-02-12',2,NULL,8,7,7),(0,_binary '','2024-02-12',3,NULL,9,4,7),(0,_binary '','2024-02-12',4,NULL,10,5,7),(4,_binary '','2024-02-16',5,NULL,11,16,7),(1,_binary '','2024-02-13',2,'2024-02-16 03:04:26.463321',12,1,11),(2,_binary '','2024-02-14',2,'2024-02-16 03:03:50.849026',13,1,11),(2,_binary '','2024-02-14',2,'2024-02-16 03:04:04.646777',14,1,11),(2,_binary '','2024-02-14',2,'2024-02-16 03:04:25.646721',15,1,11),(3,_binary '','2024-02-15',5,'2024-02-16 03:14:54.764634',16,15,11),(3,_binary '','2024-02-15',4,'2024-02-16 03:14:58.054277',17,1,11),(4,_binary '','2024-02-16',5,'2024-02-16 03:15:14.414515',18,16,11),(6,_binary '\0','2024-02-18',2,NULL,19,19,8),(4,_binary '\0','2024-02-16',5,'2024-02-16 03:47:56.663853',20,16,8),(4,_binary '\0','2024-02-16',5,'2024-02-16 04:29:11.364672',21,16,8),(4,_binary '\0','2024-02-16',2,NULL,22,23,8),(1,_binary '','2024-02-13',1,'2024-02-16 06:31:47.448745',23,7,4),(3,_binary '','2024-02-15',5,'2024-02-16 06:31:55.340832',24,15,4),(3,_binary '','2024-02-15',5,'2024-02-16 06:31:59.070968',25,4,4),(3,_binary '','2024-02-15',5,'2024-02-16 06:33:00.957912',26,15,4),(3,_binary '','2024-02-15',4,'2024-02-16 06:33:00.567370',27,9,4),(3,_binary '','2024-02-15',5,NULL,28,15,4),(3,_binary '','2024-02-15',4,NULL,29,9,4),(3,_binary '','2024-02-15',5,NULL,30,15,3),(0,_binary '','2024-02-12',2,NULL,31,8,3);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_role_update_request`
--

DROP TABLE IF EXISTS `student_role_update_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_role_update_request` (
  `before_school_type` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `before_school` varchar(255) DEFAULT NULL,
  `original_file_names` varchar(255) DEFAULT NULL,
  `quit_confirmation_paths` varchar(255) DEFAULT NULL,
  `quit_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_319yx7nk1nvue68y9mf2gcgj5` (`member_id`),
  CONSTRAINT `FK2edbqfq3d8grp3lc3aip8lgo2` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_role_update_request`
--

LOCK TABLES `student_role_update_request` WRITE;
/*!40000 ALTER TABLE `student_role_update_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_role_update_request` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-16  9:58:46
