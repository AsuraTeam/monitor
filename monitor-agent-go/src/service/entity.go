package service

type ArgsEntity struct {
	Arg1               string
	Arg2               string
	Arg3               string
	Arg4               string
	Arg5               string
	Arg6               string
	Arg7               string
	Arg8               string
}

type MonitorConfigureEntity struct {
	ConfigureId        int64
	HostId             int64
	Description        string
	MonitorTime        string
	AlarmCount         int64
	AlarmInterval      int64
	ScriptId           int64
	IsValid            int64
	LastModifyTime     string
	LastModifyUser     string
	TemplateId         string
	GroupsId           string
	Retry              int64
	MonitorConfigureTp string
	MonitorHostsTp     string
	Hosts              string
	Arg1               string
	Arg2               string
	Arg3               string
	Arg4               string
	Arg5               string
	Arg6               string
	Arg7               string
	Arg8               string
	CheckInterval      int64
	IsMobile           int64
	IsEmail            int64
	IsDing             int64
	IsWeixin           int64
	WeixinGroups       string
	DingGroups         string
	MobileGroups       string
	EmailGroups        string
	AllGroups          string
	ItemId             string
	Gname              string
}


type PushEntityValue struct {
	Ip         string
	Name       string
	Groups     string
	Value      float64
	Messages   string
	Status     int
	Command    string
	Time       string
	Server     string
}

type PushEntity struct {
	Memory     string
	Cpu        string
	Disk       string
	Sn         string
	Os         string
	AlarmId    string
	Ip         string
	Name       string
	Groups     string
	Value      string
	Messages   string
	Status     int
	Command    string
	Time       string
	GroupsName string
	ConfigId   string
	Server     string
	ScriptId   string
	ServerId   string
}




type MonitorContactGroupEntity struct {
	GroupId        int64
	GroupName      string
	Description    string
	Member         string
	Ismail         int64
	Ismobile       int64
	Status         int64
	LastModifyTime string
	LastModifyUser string
}

type MonitorContactsEntity struct {
	ContactsId     int64
	MemberName     string
	Mobile         string
	Mail           string
	No             string
	LastModifyUser string
	LastModifyTime string
}

type MonitorItemEntity struct {
	Resend          int
	IsRecover       int
	AlarmMessages   string
	RecoverMessages string
	FileName        string
	Arg1comm        string
	IsAdmin         string
	IsMerge         int
	Arg2comm        string
	Arg3comm        string
	Arg4comm        string
	Arg5comm        string
	Arg6comm        string
	Arg7comm        string
	Arg8comm        string
	ItemId          int64
	ItemName        string
	Description     string
	MonitorTime     string
	CheckInterval   string
	AlarmCount      int64
	AlarmInterval   int64
	ScriptId        int64
	Arg1            string
	Arg2            string
	Arg3            string
	Arg4            string
	IsValid         int64
	LastModifyTime  string
	LastModifyUser  string
	TemplateId      string
	Arg5            string
	Arg6            string
	Arg7            string
	Arg8            string
	Retry           int64
}

type MonitorLockEntity struct {
	ServerId  string
	CheckTime int64
}

type MonitorMessagesEntity struct {
	IndexName    string `json:"indexName"`
	Value        string `json:"value"`
	Ip           string `json:"ip"`
	IpAddress    string  `json:"ipAddress"`
	AlarmCount   int64 `json:"alarmCount"`
	MessagesId   int64 `json:"messagesId"`
	Member       string `json:"member"`
	MessagesTime string `json:"messagesTime"`
	ServerId     int64 `json:"serverId"`
	ScriptsId    int64 `json:"scriptsId"`
	GroupsId     int64 `json:"groupsId"`
	SevertityId  int64 `json:"severtityId"`
	Email        string `json:"email"`
	Mobile       string `json:"mobile"`
	Ding         string `json:"ding"`
	Weixin       string `json:"weixin"`
	Messages     string `json:"messages"`
}

type MonitorScriptsEntity struct {
	TimeOut        int64
	ScriptsId      int64
	LastModifyTime string
	LastModifyUser string
	IsValid        int64
	AlarmMess      string
	FileName       string
	Content        string
	RecoverMess    string
	Description    string
	MonitorName    string
	Arg1           string
	Arg2           string
	Arg3           string
	Arg4           string
	Arg5           string
	Arg6           string
	Arg7           string
	Arg8           string
	Arg8comm       string
	Arg7comm       string
	Arg6comm       string
	Arg5comm       string
	Arg4comm       string
	Arg3comm       string
	Arg2comm       string
	Arg1comm       string
	Anew           int64
	Isrecover      string
}

type MonitorSystemScriptsEntity struct {
	Description    string
	ScriptsId      int64
	ScriptsContent string
	OsName         string
	LastModifyTime string
	LastModifyUser string
}

type MonitorTemplateEntity struct {
	Description    string
	ItemName       string
	ItemId         string
	TemplateId     int64
	TemplateName   string
	Templates      string
	LastModifyTime string
	LastModifyUser string
	IsValid        int64
	Items          string
}