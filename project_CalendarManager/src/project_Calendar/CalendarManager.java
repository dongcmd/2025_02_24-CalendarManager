package project_Calendar;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CalendarManager { // yyyy/MM/dd HH:mm:ss 2025/02/26 10:33:33
	Scanner sc = new Scanner(System.in);
	Map<String, List<Event> > eventDays = new HashMap<>();
	String id;
	
	void run() throws IOException, ClassNotFoundException, ParseException {
		
		System.out.print("사용자 아이디를 입력하세요 >> ");
//		id = sc.nextLine().trim();
		System.out.println("dw");
		id = "dw";
		Object obj = FileIO.load(id);
		if(obj instanceof HashMap) {
			eventDays = (HashMap<String, List<Event>>)obj;
			System.out.println(id + "님 환영합니다.");
		} else {
//			연습용 데이터를 추가하는 문장
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//			Date d = sdf.parse("2025/02/26 10:00:00");
//			List<Event> list = new ArrayList<>();
//			list.add(new Event("연습1", "설명", d, d));
//			Date e = sdf.parse("2025/02/27 10:00:00");
//			List<Event> list2 = new ArrayList<>();
//			list2.add(new Event("연습1", "설명", e, e));
//			eventDays.put("2025/02/26", list);
//			eventDays.put("2025/02/27", list2);
			System.out.println("저장된 데이터가 없습니다. 새로운 사용자로 시작합니다.");
			FileIO.save(id, eventDays);
		}
		
		
		while(true) {
//			list.add(new Event("d", "s", new Date(), new Date()));
//			list.add(new Event("d", "s", new Date(), new Date()));
//			list.add(new Event("d", "s", new Date(), new Date()));
//			list.add(new Event("d", "s", new Date(), new Date()));
//			list.add(new Event("test", "test", new Date(), new Date()));
//			eventDays.put("2025/02/26", list);
			System.out.println("메뉴를 선택하세요.\n1:종료 | 2:달력조회 | 3:이벤트추가 | 4.이벤트조회 | 5.이벤트변경 | 6.이벤트삭제");
			try {
				int menu = sc.nextInt(); sc.nextLine();
				if(!(1<=menu&&menu<=6)) { System.out.println("1~6 사이 숫자 입력하세요."); continue; }
				switch(menu) {
				case 1 : 
					FileIO.save(id, eventDays);
					System.out.println(id + "님 또 봐요.");
					System.out.println("프종 bye"); return; 
				case 2 : printCal(); continue;
				case 3 : addEvent(); continue;
				case 4 : searchEvent(); continue;
				case 5 : changeEvent(); continue;
				case 6 : deleteEvent(); continue;
				}
			} catch(InputMismatchException e) { System.out.println("메뉴에 해당하는 숫자를 입력하세요."); sc.next(); }
		}
	}
	
	private void printCal() throws NullPointerException, IOException  { // 2. 달력출력
		int year, month;
		try {
			System.out.print("조회할 달력의 년도를 입력하세요(yyyy) >> ");
			year = sc.nextInt(); sc.nextLine();
			System.out.print("조회할 달력의 월을 입력하세요(m) >> ");
			month = sc.nextInt(); sc.nextLine();
//			year = 2025; month = 4;
			if(!(1<=month && month<=12)) { System.out.println("월 범위를 초과했습니다. 이전 메뉴로 돌아갑니다."); return; }
			System.out.printf("%14s%d 년 %02d 월\n", " ",year, month); // 년 월 출력
			String[] days = "Sun,Mon,Tues,Wed,Thur,Fri,Sat".split(",");
			for(String s : days) { System.out.printf("%-7s", s); } // 요일 출력
			System.out.println();
			Calendar cal = Calendar.getInstance();
			cal.set(year, month-1, 1);
			int date = 1;
			
			for(int i = 1; i < 8; i++) {
				String dateString = String.format("%4d/%02d/%02d", year, month, date);
				if(cal.get(Calendar.DAY_OF_WEEK) <= i)
					if(eventDays.get(dateString) != null) {
						List<Event> list = new ArrayList<>();
						list = eventDays.get(dateString);
						System.out.printf("%-7s", (date++) +"("+list.size() +")");
					} else {
						System.out.printf("%-7d", date++);
					}
				else
					System.out.printf("%7s", " ");
			} System.out.println();
			
			int cnt = 0;
			while(date <= cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
				cnt++;
				String dateString = String.format("%4d/%02d/%02d", year, month, date);
				if(eventDays.get(dateString) != null) {
					List<Event> list = new ArrayList<>();
					list = eventDays.get(dateString);
					System.out.printf("%-7s", (date++) +"("+list.size() +")");
				} else {
					System.out.printf("%-7d", (date++));
				}
				if(cnt == 7) {
					cnt = 0;
					System.out.println();
				}
			}
			System.out.println();
		} catch(InputMismatchException e) { System.out.println("범위 내 숫자만 입력하세요."); }
	}
	private void addEvent() throws IOException { // 3. 추가
		String name, desc, inputDate, dateString;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date sDate, eDate;
		
		try {
			System.out.print("이벤트 제목 입력 >> ");
			name = sc.nextLine();
			System.out.print("이벤트 시작 시각 입력 (yyyy/MM/dd HH:mm:ss) >> ");
			inputDate = sc.nextLine();
			sDate = sdf.parse(inputDate);
			System.out.print("이벤트 종료 시각 입력 (yyyy/MM/dd HH:mm:ss) >> ");
			inputDate = sc.nextLine();
			eDate = sdf.parse(inputDate);
			if(sDate.after(eDate)) {
				System.out.println("종료시각이 시작시각보다 빠를 수 없습니다. 이전 메뉴로 돌아갑니다.");
				return;
			}
			System.out.print("이벤트 세부사항 입력 >> ");
			desc = sc.nextLine();
		} catch(ParseException e) {
			System.out.println("이벤트 양식이 잘못되었습니다. 이전메뉴로 돌아갑니다.");
			return;
		}
		sdf = new SimpleDateFormat("yyyy/MM/dd");
		dateString = sdf.format(sDate);
		Event e = new Event(name, desc, sDate, eDate);

		if( eventDays.get(dateString) != null) {
			eventDays.get(dateString).add(e);
			System.out.println("해당 일의 이벤트 추가완료");
		} else {
			List<Event> list = new ArrayList<>();
			list.add(e);
			eventDays.put(dateString, list);
			System.out.println("해당 일의 이벤트 생성완료");
		}
		FileIO.save(id, eventDays);
	}
	private void searchEvent() { // 4.이벤트 조회
		System.out.print("조회할 이벤트 날짜를 입력하세요(yyyy/MM/dd) >> ");
		String inputDate = sc.nextLine();
		if(eventDays.get(inputDate) != null) {
			List<Event> list = new ArrayList<>();
			list = eventDays.get(inputDate);
			Collections.sort(list);
			for(Event e : list) {
				System.out.print(e);
				System.out.println();
			}
		} else {
			System.out.println("해당 날짜에 이벤트가 없습니다. 이전 메뉴로 돌아갑니다.");
		}
	}
	private void changeEvent() throws IOException { // 5.이벤트 변경
		System.out.print("변경할 이벤트 날짜 (yyyy/MM/dd) >> ");
		String input = sc.nextLine().trim();
		String inputDate = input;
		List<Event> list = new ArrayList<>();
		list = eventDays.get(input);
		Collections.sort(list);
		for(Event e : list) {
			System.out.printf("번호 : %d =>" + e, list.indexOf(e));
			System.out.println();
		}
		try {
			System.out.println("수정할 번호를 선택하세요 >> ");
			input = sc.nextLine().trim();
			System.out.println("수정 내용 확인 : ");
		} catch(IndexOutOfBoundsException ioex) {
			System.out.println("해당하는 이벤트가 없습니다. 이전 메뉴"); return;
		}
		
		Event e = list.get(Integer.parseInt(input));
		System.out.print(e);
		System.out.println("이벤트를 변경하시려면 Y를 입력하세요.");
		input = sc.nextLine().trim();
		if(input.equalsIgnoreCase("y")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String name, desc;
			Date sDate, eDate;
			try {
				System.out.printf("%s => 변경할 제목 입력 >> ", e.name);
				name = sc.nextLine().trim();
				System.out.printf(e.getSDate() + "=> 변경할 시작 시각 입력 >> ");
				sDate = sdf.parse(sc.nextLine().trim());
				System.out.printf(e.getEDate() + "=> 변경할 종료 시각 입력 >> ");
				eDate = sdf.parse(sc.nextLine().trim());
				if(sDate.after(eDate)) {
					System.out.println("종료시각이 시작시각보다 빠를 수 없습니다. 이전 메뉴로 돌아갑니다.");
					return;
				}
			System.out.printf("%s => 변경할 세부사항 입력 >> ", e.desc);
			desc = sc.nextLine().trim();
			list.remove(e);
			if(list.size() == 0) {
				eventDays.remove(inputDate);
			}
			
			sdf = new SimpleDateFormat("yyyy/MM/dd");
			String sDateString = sdf.format(sDate);
			e = new Event(name, desc, sDate, eDate);
			if( eventDays.get(sDateString) != null) {
				eventDays.get(sDateString).add(e);
			} else {
				List<Event> list2 = new ArrayList<>();
				list2.add(e);
				eventDays.put(sDateString, list2);
			}
			
			} catch(ParseException excep) {
				System.out.println("이벤트 양식이 잘못되었습니다. 이전메뉴로 돌아갑니다.");
				return;
			}
			System.out.println("이벤트 변경 완료");
			
		} else {
			System.out.println("이전 메뉴로 돌아갑니다.");
		}
		FileIO.save(id, eventDays);
	}
	private void deleteEvent() throws IOException { // 6.이벤트 삭제
		System.out.print("삭제할 이벤트 날짜 (yyyy/MM/dd) >> ");
		String input = sc.nextLine().trim();
		String inputDate = input;
		List<Event> list = new ArrayList<>();
		list = eventDays.get(input);
		Collections.sort(list);
		for(Event e : list) {
			System.out.printf("번호:%d=>" + e, list.indexOf(e));
			System.out.println();
			
		}
		System.out.println("삭제할 번호를 선택하세요 >> ");
		input = sc.nextLine().trim();
		System.out.println("삭제 내용 확인 : ");
		try {
			list.get(Integer.parseInt(input));
		} catch(IndexOutOfBoundsException ioex) {
			System.out.println("해당하는 이벤트가 없습니다. 이전 메뉴"); return;
		}
		Event e = list.get(Integer.parseInt(input));
		System.out.print(e);
		System.out.println("이벤트를 삭제하려면 Y를 입력하세요.");
		input = sc.nextLine().trim();
		if(input.equalsIgnoreCase("y") && list.remove(e)) {
			System.out.println("이벤트 삭제 완료");
			if(list.size() == 0) {
				eventDays.remove(inputDate);
			}
			
		} else {
			System.out.println("이전 메뉴로 돌아갑니다.");
		}
		FileIO.save(id, eventDays);
	}
}