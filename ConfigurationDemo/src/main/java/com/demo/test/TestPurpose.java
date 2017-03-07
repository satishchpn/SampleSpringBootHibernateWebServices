/*package com.demo.test;

import java.util.List;


public class TestPurpose {
	private static List<Room> roomList;

	public static List<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	public static void main(String[] args) {
		long count = getRoomList().stream().filter(room -> room.isSelectedRoom()).count();
		List<TransactionStructure> tempFrontOfficeTransactionStructureList = billSplitStructure.getTransactionStructureList().stream().filter(transactionStructure -> !transactionStructure.isTax()).collect(Collectors.toList());
		BillSplitStructure billSplitStructure roomViewBillSplit.getBill().getFrontOfficeTransactionList().stream().filter(trn -> (!RecordCode.NO_TRANSACTION.equals(trn.getRecordCode()) && trn.getBookingGuestDetailId() > 0 && revenueCode.getRevenueCodeId() == trn.getRevenueCodeId().getRevenueCodeId())).forEach(trnChange -> trnChange.setNew_description(newValue));
		Map<Long, List<BookingGuestDetail>> guestByReferenceNo = searchAllBookingGuestDetailList.stream().collect(Collectors.groupingBy(BookingGuestDetail::getCheckInReferenceNumber));
		int adult = (int) list.stream().filter(bgd -> bgd.getAdultType() == AdultType.Adult || bgd.getAdultType() == AdultType.ExtraAdult).count();
		Map<Long, List<BookingGuestDetail>> mapByCheckInLinkRef = tempBookingGuestDetailList.stream().collect(Collectors.groupingBy(BookingGuestDetail::getCheckInReferenceNumber));
		for (Map.Entry<Long, List<BookingGuestDetail>> entry : mapByCheckInLinkRef.entrySet()) {
			List<BookingGuestDetail> list = entry.getValue();
			int adult = (int) list.stream().filter(bgd -> bgd.getAdultType() == AdultType.Adult || bgd.getAdultType() == AdultType.ExtraAdult).count();
			int child = (int) list.stream().filter(bgd -> bgd.getAdultType() == AdultType.Child || bgd.getAdultType() == AdultType.ExtraChild).count();
			getCheckOutDisplayStructureList().stream().filter(str -> str.getBookingGuestDetail().getCheckInReferenceNumber() == list.get(0).getCheckInReferenceNumber() && str.getBookingGuestDetail().getFolioNumber().contains(list.get(0).getFolioNumber())).forEach(str -> {
				str.setPersons((adult > 0 ? "Adult " + adult : "") + (child > 0 && adult > 0 ? " + " : "") + (child > 0 ? " Child " + child : ""));
			});
		}
		
		Map<String, List<BookingGuestDetail>> roomFolioGuestList = eachRoom.getValue().stream().collect(Collectors.groupingBy(BookingGuestDetail::getFolioNumber));
		roomFolioGuestList.entrySet().forEach(folioGuestList -> {
			//List<BookingGuestDetail> temp = folioGuestList.getValue().stream().filter(folioGuest -> WebUtil.roundDateDown(folioGuest.getDepartureDate()).equals((WebUtil.roundDateDown(checkOutSearchStructure.getAccountingDate())))).collect(Collectors.toList());
			List<BookingGuestDetail> temp = new ArrayList<BookingGuestDetail>();
			for (BookingGuestDetail bg1 : folioGuestList.getValue()) {
				try {
					if (webUtilMB.formateDate(WebUtil.roundDateDown(checkOutSearchStructure.getAccountingDate())).equals(webUtilMB.formateDate(webUtilMB.convertServerDateToPropertyDate(cashierLoginMB.getCurrentProperty().getPropertyId(), bg1.getDepartureDate(), bg1.getDepartureTime())))) {
						temp.add(bg1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		Set<BookingGuestDetail> tempMainGuestDetailList = new HashSet<BookingGuestDetail>();
		tempSearchBookingGuestDetailList.stream().forEach(inHouseGuest -> tempMainGuestDetailList.addAll(guestByReferenceNo.get(inHouseGuest.getCheckInReferenceNumber()).stream().filter(mainGuest -> (mainGuest.getGuestType().equals(GuestType.Main) && inHouseGuest.getFolioNumber().equals(mainGuest.getFolioNumber()))).collect(Collectors.toList())));
}

}
*/