package event.list
{
	import flash.events.Event;

	public class ListClickEnent extends Event
	{
		public static var rowClick:String="rowClick";
		public var key:String=null;
		public var obj:Object=null;
		public var doc:XML=null;
		public function ListClickEnent(key:String,obj:Object)
		{
			super(rowClick);
			this.key=key;
			this.obj=obj;
		}
		
	}
}