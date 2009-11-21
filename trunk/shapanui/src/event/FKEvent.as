package event
{
	import flash.events.Event;

	public class FKEvent extends Event
	{
		
		public static var DATA_CHANGE:String="dataChange";
		private var obj:Object=null;
		public function FKEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		public function setCN(obj:Object):void{
			this.obj=obj;
		}
		public function getCN():Object{
			return this.obj;
		}
		
	}
}