package com.pioneer
{
	import mx.binding.*;
  import mx.charts.*;
  import mx.charts.series.*;
  import mx.collections.*;
  import mx.containers.*;
  import mx.controls.*;
  import mx.core.*;
  import mx.events.*;
  import mx.graphics.*;

  public class Scaler extends Canvas
  {
    private var _bindings:Array;
    public var tempFills:Array;
    public var _bindingsByDestination:Object;
    private var _embed_mxml__com_colapht_component_scaler_assets_zhizhen_gif_1297553571:Class;
    private var _1331206862scalerbgimage:Image;
    private var _796122915polarimage:Image;
    private var _1112240437digitalShow:Button;
    public var zonedata:ArrayCollection;
    private var _492527759iconSymbol:Class;
    public var duration:Number;
    public var colorfills:String;
    public var _bindingsBeginWithWord:Object;
    private var _572741379pieseries:PieSeries;
    private var _embed_mxml__com_colapht_component_scaler_assets_yibiaopan1_png_440847971:Class;
    public var startvalue:Number;
    public var maxvalue:Number;
    private var _51492982scalerchart:PieChart;
    private var _919806160runner:Rotation;
    private var _watchers:Array;
    public var zonevalue2:Number;
    public var currentvalue:Number;
    public var zonevalue1:Number;
    private var _documentDescriptor_:UIComponentDescriptor;
    static private var _watcherSetupUtil:IWatcherSetupUtil;

    public function Scaler()
    {
        _documentDescriptor_ = new UIComponentDescriptor({type:Canvas, propertiesFactory:
function () : Object
{
  return {creationPolicy:"all", width:227, height:117, childDescriptors:[new UIComponentDescriptor({type:Image, id:"scalerbgimage", propertiesFactory:
function () : Object
{
  return {source:_embed_mxml__com_colapht_component_scaler_assets_yibiaopan1_png_440847971, width:227, height:117};
}
}), new UIComponentDescriptor({type:PieChart, id:"scalerchart", stylesFactory:
function () : void
{
  this.innerRadius = 0.7;
  return;
}
, propertiesFactory:
function () : Object
{
  return {height:225, width:256, alpha:0.6, x:-14, y:3, series:[_PieSeries1_i()]};
}
}), new UIComponentDescriptor({type:Image, id:"polarimage", propertiesFactory:
function () : Object
{
  return {source:_embed_mxml__com_colapht_component_scaler_assets_zhizhen_gif_1297553571, width:93, x:115, y:114};
}
}), new UIComponentDescriptor({type:Button, id:"digitalShow", stylesFactory:
function () : void
{
  this.borderColor = 0;
  this.cornerRadius = 7;
  this.focusThickness = 0;
  this.fontThickness = 10;
  this.horizontalGap = 0;
  this.textAlign = "center";
  this.textRollOverColor = 16777215;
  this.fontGridFitType = "pixel";
  this.fillColors = [11184810];
  return;
}
, propertiesFactory:
function () : Object
{
  return {x:85.5, y:57, width:56, enabled:false, emphasized:true};
}
})]};
}
});
        _492527759iconSymbol = Scaler_iconSymbol;
        tempFills = new Array();
        zonedata = new ArrayCollection();
        _embed_mxml__com_colapht_component_scaler_assets_yibiaopan1_png_440847971 = Scaler__embed_mxml__com_colapht_component_scaler_assets_yibiaopan1_png_440847971;
        _embed_mxml__com_colapht_component_scaler_assets_zhizhen_gif_1297553571 = Scaler__embed_mxml__com_colapht_component_scaler_assets_zhizhen_gif_1297553571;
        mx_internal::_document = this;
        this.creationPolicy = "all";
        this.verticalScrollPolicy = "off";
        this.horizontalScrollPolicy = "off";
        this.width = 227;
        this.height = 117;
        _Rotation1_i();
        this.addEventListener("initialize", ___Canvas1_initialize);
        return;
    }

    private function _Scaler_bindingsSetup() : void
    {
        var binding:Binding;
        if (!_bindings)
        {
          _bindings = [];
        }// end if
        if (!_watchers)
        {
          _watchers = [];
        }// end if
        binding = new Binding(this, 
function () : Number
{
  return Math.abs(currentvalue - startvalue) * 180 / Math.abs(maxvalue - startvalue);
}
, 
function (param1:Number) : void
{
  runner.angleTo = param1;
  return;
}
, "runner.angleTo");
        _bindings[0] = binding;
        binding = new Binding(this, 
function () : Number
{
  return duration;
}
, 
function (param1:Number) : void
{
  runner.duration = param1;
  return;
}
, "runner.duration");
        _bindings[1] = binding;
        binding = new Binding(this, 
function () : Object
{
  return zonedata;
}
, 
function (param1:Object) : void
{
  scalerchart.dataProvider = param1;
  return;
}
, "scalerchart.dataProvider");
        _bindings[2] = binding;
        binding = new Binding(this, 
function () : Array
{
  return tempFills;
}
, 
function (param1:Array) : void
{
  pieseries.setStyle("fills", param1);
  return;
}
, "pieseries.fills");
        _bindings[3] = binding;
        binding = new Binding(this, 
function ()
{
  return runner;
}
, 
function (param1) : void
{
  polarimage.setStyle("creationCompleteEffect", param1);
  return;
}
, "polarimage.creationCompleteEffect");
        _bindings[4] = binding;
        binding = new Binding(this, 
function () : String
{
  var _loc_1:*;
  var _loc_2:*;
  _loc_1 = currentvalue;
  _loc_2 = _loc_1 == undefined ? (null) : (String(_loc_1));
  return _loc_2;
}
, 
function (param1:String) : void
{
  digitalShow.label = param1;
  return;
}
, "digitalShow.label");
        _bindings[5] = binding;
        binding = new Binding(this, 
function () : uint
{
  return getDisabledColor();
}
, 
function (param1:uint) : void
{
  digitalShow.setStyle("disabledColor", param1);
  return;
}
, "digitalShow.disabledColor");
        _bindings[6] = binding;
        return;
    }

    public function doInit() : void
    {
        var _loc_1:Object;
        var _loc_2:Object;
        var _loc_3:Object;
        var _loc_4:Object;
        var _loc_5:Number;
        var _loc_6:SolidColor;
        var _loc_7:String;
        zonedata.removeAll();
        _loc_1 = new Object();
        _loc_1.percentage = Math.abs(maxvalue - zonevalue2);
        zonedata.addItem(_loc_1);
        _loc_2 = new Object();
        _loc_2.percentage = Math.abs(zonevalue2 - zonevalue1);
        zonedata.addItem(_loc_2);
        _loc_3 = new Object();
        _loc_3.percentage = Math.abs(zonevalue1 - startvalue);
        zonedata.addItem(_loc_3);
        _loc_4 = new Object();
        _loc_4.percentage = Math.abs(maxvalue - startvalue);
        zonedata.addItem(_loc_4);
        _loc_5 = 0;
        while (_loc_5++ < colorfills.length)
        {
          // label
          _loc_7 = colorfills.charAt(_loc_5);
          switch(_loc_7)
          {
            case "R":
            {
                _loc_6 = new SolidColor(16711680, 0.8);
                tempFills.push(_loc_6);
                break;
            }
            case "Y":
            {
                _loc_6 = new SolidColor(16776960, 0.8);
                tempFills.push(_loc_6);
                break;
            }
            case "G":
            {
                _loc_6 = new SolidColor(6736896, 0.8);
                tempFills.push(_loc_6);
                break;
            }
            default:
            {
                break;
            }
          }
        }
        tempFills.reverse();
        _loc_6 = new SolidColor(0, 0.8);
        tempFills.push(_loc_6);
        runner.angleTo = Math.abs(currentvalue - startvalue) * 180 / Math.abs(maxvalue - startvalue);
        runner.angleFrom = 0;
        runner.duration = duration;
        digitalShow.label = currentvalue + "";
        digitalShow.setStyle("disabledColor", getDisabledColor());
        return;
    }

    public function ___Canvas1_initialize(param1:FlexEvent) : void
    {
        doInit();
        return;
    }

    public function get scalerchart() : PieChart
    {
        return this._51492982scalerchart;
    }

    public function get polarimage() : Image
    {
        return this._796122915polarimage;
    }

    public function set scalerchart(param1:PieChart) : void
    {
        var _loc_2:Object;
        _loc_2 = this._51492982scalerchart;
        if (_loc_2 !== param1)
        {
          this._51492982scalerchart = param1;
          dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "scalerchart", _loc_2, param1));
        }// end if
        return;
    }

    public function get digitalShow() : Button
    {
        return this._1112240437digitalShow;
    }

    private function _Scaler_bindingExprs() : void
    {
        var _loc_1:*;
        _loc_1 = Math.abs(currentvalue - startvalue) * 180 / Math.abs(maxvalue - startvalue);
        _loc_1 = duration;
        _loc_1 = zonedata;
        _loc_1 = tempFills;
        _loc_1 = runner;
        _loc_1 = currentvalue;
        _loc_1 = getDisabledColor();
        return;
    }

    public function set scalerbgimage(param1:Image) : void
    {
        var _loc_2:Object;
        _loc_2 = this._1331206862scalerbgimage;
        if (_loc_2 !== param1)
        {
          this._1331206862scalerbgimage = param1;
          dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "scalerbgimage", _loc_2, param1));
        }// end if
        return;
    }

    public function set polarimage(param1:Image) : void
    {
        var _loc_2:Object;
        _loc_2 = this._796122915polarimage;
        if (_loc_2 !== param1)
        {
          this._796122915polarimage = param1;
          dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "polarimage", _loc_2, param1));
        }// end if
        return;
    }

    private function _Rotation1_i() : Rotation
    {
        var _loc_1:Rotation;
        _loc_1 = new Rotation();
        runner = _loc_1;
        _loc_1.angleFrom = 0;
        BindingManager.executeBindings(this, "runner", runner);
        return _loc_1;
    }

    public function set pieseries(param1:PieSeries) : void
    {
        var _loc_2:Object;
        _loc_2 = this._572741379pieseries;
        if (_loc_2 !== param1)
        {
          this._572741379pieseries = param1;
          dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "pieseries", _loc_2, param1));
        }// end if
        return;
    }

    public function set runner(param1:Rotation) : void
    {
        var _loc_2:Object;
        _loc_2 = this._919806160runner;
        if (_loc_2 !== param1)
        {
          this._919806160runner = param1;
          dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "runner", _loc_2, param1));
        }// end if
        return;
    }

    public function set digitalShow(param1:Button) : void
    {
        var _loc_2:Object;
        _loc_2 = this._1112240437digitalShow;
        if (_loc_2 !== param1)
        {
          this._1112240437digitalShow = param1;
          dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "digitalShow", _loc_2, param1));
        }// end if
        return;
    }

    public function getDisabledColor() : uint
    {
        var _loc_1:uint;
        var _loc_2:SolidColor;
        var _loc_3:SolidColor;
        var _loc_4:SolidColor;
        _loc_1 = new uint(16777215);
        if (Math.abs(currentvalue - startvalue) < Math.abs(zonevalue1 - startvalue) && Math.abs(currentvalue - startvalue) > 0)
        {
          _loc_2 = SolidColor(tempFills[2]);
          _loc_1 = _loc_2.color;
        }
        else if (Math.abs(currentvalue - startvalue) > Math.abs(zonevalue1 - startvalue) && Math.abs(currentvalue - startvalue) < Math.abs(zonevalue2 - startvalue))
        {
          _loc_3 = SolidColor(tempFills[1]);
          _loc_1 = _loc_3.color;
        }
        else if (Math.abs(currentvalue - startvalue) > Math.abs(zonevalue2 - startvalue) && Math.abs(currentvalue - startvalue) < Math.abs(maxvalue - startvalue))
        {
          _loc_4 = SolidColor(tempFills[0]);
          _loc_1 = _loc_4.color;
        }// end else if
        if (_loc_1 == new uint("0xff0000"))
        {
        }// end if
        return _loc_1;
    }

    public function get scalerbgimage() : Image
    {
        return this._1331206862scalerbgimage;
    }

    public function set iconSymbol(param1:Class) : void
    {
        var _loc_2:Object;
        _loc_2 = this._492527759iconSymbol;
        if (_loc_2 !== param1)
        {
          this._492527759iconSymbol = param1;
          dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "iconSymbol", _loc_2, param1));
        }// end if
        return;
    }

    public function get pieseries() : PieSeries
    {
        return this._572741379pieseries;
    }

    public function get runner() : Rotation
    {
        return this._919806160runner;
    }

    public function get iconSymbol() : Class
    {
        return this._492527759iconSymbol;
    }

    public override function initialize() : void
    {
        var target:Scaler;
        .mx_internal::setDocumentDescriptor(_documentDescriptor_);
        _Scaler_bindingsSetup();
        target;
        _watcherSetupUtil.setup(this, 
function (param1:String)
{
  return target[param1];
}
, _bindings, _watchers);
        super();
        return;
    }

    private function _PieSeries1_i() : PieSeries
    {
        var _loc_1:PieSeries;
        _loc_1 = new PieSeries();
        pieseries = _loc_1;
        _loc_1.field = "percentage";
        _loc_1.id = "pieseries";
        BindingManager.executeBindings(this, "pieseries", pieseries);
        if (!_loc_1.document)
        {
          _loc_1.document = this;
        }// end if
        return _loc_1;
    }

    static public final function set watcherSetupUtil(param1:IWatcherSetupUtil) : void
    {
        Scaler._watcherSetupUtil = param1;
        return;
    }
  }

}