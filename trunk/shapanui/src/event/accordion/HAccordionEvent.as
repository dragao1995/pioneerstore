package event.accordion
{
	import flash.events.Event;

	public class HAccordionEvent extends Event
	{
		public static var  HACCORTION_CLICK:String="haccortionClick";
		
		var accordionName:String =null;
		
		public function HAccordionEvent(accordionName:String)
		{
			super(HACCORTION_CLICK);
			this.accordionName=accordionName;
		}
		
		public function getAccordionName():String{
			return this.accordionName;
		}
		
	}
}