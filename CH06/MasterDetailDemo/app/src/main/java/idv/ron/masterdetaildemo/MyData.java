package idv.ron.masterdetaildemo;


public final class MyData {
	// 以陣列儲存4個Park物件
	public static final Park[] PARKS = {
			new Park(
					"墾丁國家公園",
					R.drawable.kenting,
					"墾丁國家公園是台灣第1座成立的國家公園，1984年1月1日成立管理處，三面臨海，為我國少數同時涵蓋陸域與海域的國家公園之一，海域面積15206.09公頃、陸域面積18083.50公頃，合計共33,289.59公頃。"),
			new Park("玉山國家公園", R.drawable.yushan,
					"玉山國家公園是台灣第2座國家公園，1985年4月10日成立管理處，面積10萬5,490公頃，屬一典型高山國家公園。"),
			new Park(
					"陽明山國家公園",
					R.drawable.yangmingshan,
					"陽明山國家公園前身為日治時期成立之大屯國立公園，1985年9月16日成立管理處。位於台北市近郊，行政區域包括同市北投區、士林區，新北市的萬里區、金山區、三芝區、淡水區一帶；地理上則屬於大屯火山彙區域。"),
			new Park("太魯閣國家公園", R.drawable.taroko,
					"太魯閣國家公園前身為日治時期成立之次高太魯閣國立公園，1986年11月28日成立管理處。位於台灣東部，地跨花蓮縣、台中市、南投縣三個行政區。") };

	static class Park {
		private String name; // 國家公園名稱
		private int imageId; // 國家公園圖片resource id
		private String description; // 國家公園描述

		public Park() {

		}

		public Park(String name, int imageId, String description) {
			super();
			this.name = name;
			this.imageId = imageId;
			this.description = description;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getImageId() {
			return imageId;
		}

		public void setImageId(int imageId) {
			this.imageId = imageId;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
}
