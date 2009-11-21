package view.page
{
	import mx.controls.Alert;
	
	public class CtrlPage
	{
		public function CtrlPage()
		{
		}
		private var xmlData:XML=null;//数据源
		private var lineNum:int=0;//数据条数
		private var lineNumOfPage:int=10;//每页的条数
		private var pageNum:int=0;//页数
		private var currentPage:int=1;//当前要显示的页数
		private var startIndex=0;//开始索引
		private var endIndex=10;//结束索引
		public function setData(xmlData:XML):void{//本例子使用/baseDatePrv 工程中定义的格式。
			
			if(null!=xmlData){
				
				var datarows:XMLList=xmlData.TABLEDATA.ROWDATA.ROW;
				this.lineNum=datarows.length();
				this.pageNum=this.lineNum/this.lineNumOfPage;
				if(this.lineNum%this.lineNumOfPage!=0)
					this.pageNum++;
				
			}
		}
		
		
	}
}