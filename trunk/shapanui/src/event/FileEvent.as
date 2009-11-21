package event
{
	import flash.events.Event;

	public class FileEvent extends Event
	{
		
		public static var UPLOAD_SUCESSFUL:String="uploadSucessful";
		private var fileName:String
		public function FileEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		public function setFileName(fileName:String):void{
			this.fileName=fileName;
		}
		public function getFileName():String{
			return this.fileName;
		}
//		var e:Event =new Event();
		
	}
}