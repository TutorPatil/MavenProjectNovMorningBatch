FileUplaod();

Func FileUplaod()

WinActivate("Open");
ControlSetText("Open","","Edit1","D:\test.txt");
ControlClick("Open","","Button1");



EndFunc