package event.tree
{
	import flash.events.Event;

	public class TreeEvent extends Event
	{
						  //TREE_SELECT
		public static var TREE_SELECTED:String="treeSelect";
		public var treeNode:Object=null;
		public function TreeEvent(node:Object)
		{
			super(TREE_SELECTED);
			this.treeNode=node;
		}
	}
}