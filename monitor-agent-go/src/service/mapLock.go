package service

import (
	"sync"
)

type MapLockString struct {
	Data map[string]string
	Lock sync.RWMutex
}

type MapLockInt64 struct {
	Data map[string]int64
	Lock sync.RWMutex
}

type MapLockStringList struct {
	Data map[string][]string
	Lock sync.RWMutex
}

type MapLockPushEntity struct {
	Data map[string]PushEntity
	Lock sync.RWMutex
}

type MapLockMonitorScriptEntity struct {
	Data map[string]MonitorScriptsEntity
	Lock sync.RWMutex
}

type MapLockMonitorItemEntity struct {
	Data map[string]MonitorItemEntity
	Lock sync.RWMutex
}

type MapLockMonitorConfigureEntity struct {
	Data map[string]MonitorConfigureEntity
	Lock sync.RWMutex
}

func (m *MapLockStringList) Get(k string) ([]string, bool) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k], true
	} else {
		defer m.Lock.RUnlock()
	}
	return make([]string, 0), false
}

func (m *MapLockStringList) Set(k string, v []string) {
	m.Lock.Lock()
	if len(m.Data) < 1 {
		m.Data = make(map[string][]string)
	}
	m.Data[k] = v
	defer m.Lock.Unlock()
}

func (m *MapLockMonitorConfigureEntity) Get(k string) (MonitorConfigureEntity, bool) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k], true
	} else {
		defer m.Lock.RUnlock()
	}
	return MonitorConfigureEntity{}, false
}

func (m *MapLockMonitorConfigureEntity) GetData() (map[string]MonitorConfigureEntity) {
	m.Lock.RLock()
	md := m.Data
	defer m.Lock.RUnlock()
	return md
}

func (m *MapLockMonitorScriptEntity) Set(k string, v MonitorScriptsEntity) {
	m.Lock.Lock()
	if len(m.Data) < 1 {
		m.Data = make(map[string]MonitorScriptsEntity)
	}
	m.Data[k] = v
	defer m.Lock.Unlock()
}

func (m *MapLockMonitorScriptEntity) Get(k string) (MonitorScriptsEntity, bool) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k], true
	} else {
		defer m.Lock.RUnlock()
	}
	return MonitorScriptsEntity{}, false
}

func (m *MapLockMonitorConfigureEntity) Set(k string, v MonitorConfigureEntity) {
	m.Lock.Lock()
	if len(m.Data) < 1 {
		m.Data = make(map[string]MonitorConfigureEntity)
	}
	m.Data[k] = v
	defer m.Lock.Unlock()
}

func (m *MapLockMonitorItemEntity) Get(k string) (MonitorItemEntity, bool) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k], true
	} else {
		defer m.Lock.RUnlock()
	}
	return MonitorItemEntity{}, false
}

func (m *MapLockMonitorItemEntity) Set(k string, v MonitorItemEntity) {
	m.Lock.Lock()
	if len(m.Data) < 1 {
		m.Data = make(map[string]MonitorItemEntity)
	}
	m.Data[k] = v
	defer m.Lock.Unlock()
}

func (m *MapLockPushEntity) Get(k string) (PushEntity, bool) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k], true
	} else {
		defer m.Lock.RUnlock()
	}
	return PushEntity{}, false
}

func (m *MapLockPushEntity) GetV(k string) (PushEntity) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k]
	} else {
		defer m.Lock.RUnlock()
	}
	return PushEntity{}
}

func (m *MapLockPushEntity) Delete(k string) {
	m.Lock.Lock()
	delete(m.Data, k)
	defer m.Lock.Unlock()
}

func (m *MapLockPushEntity) Set(k string, v PushEntity) {
	m.Lock.Lock()
	if len(m.Data) < 1 {
		m.Data = make(map[string]PushEntity)
	}
	m.Data[k] = v
	defer m.Lock.Unlock()
}

func (m *MapLockInt64) Get(k string) (int64, bool) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k], true
	} else {
		defer m.Lock.RUnlock()
	}
	return 0, false
}

func (m *MapLockInt64) GetData() (map[string]int64) {
	m.Lock.RLock()
	dm := m.Data
	defer m.Lock.RUnlock()
	return dm
}

func (m *MapLockInt64) GetV(k string) (int64) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k]
	} else {
		defer m.Lock.RUnlock()
	}
	return 0
}

func (m *MapLockInt64) Delete(k string) {
	m.Lock.Lock()
	delete(m.Data, k)
	defer m.Lock.Unlock()
}

func (m *MapLockInt64) Set(k string, v int64) {
	m.Lock.Lock()
	if len(m.Data) < 1 {
		m.Data = make(map[string]int64)
	}
	m.Data[k] = v
	defer m.Lock.Unlock()
}

func (m *MapLockString) Get(k string) (string, bool) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k], true
	} else {
		defer m.Lock.RUnlock()
	}
	return "", false
}

func (m *MapLockString) GetV(k string) (string) {
	m.Lock.RLock()
	if _, ok := m.Data[k]; ok {
		defer m.Lock.RUnlock()
		return m.Data[k]
	} else {
		defer m.Lock.RUnlock()
	}
	return ""
}

func (m *MapLockString) GetData() (map[string]string) {
	m.Lock.RLock()
	md := m.Data
	defer m.Lock.RUnlock()
	return md
}

func (m *MapLockString) Set(k, v string) {
	m.Lock.Lock()
	if len(m.Data) < 1 {
		m.Data = make(map[string]string)
	}
	m.Data[k] = v
	defer m.Lock.Unlock()
}

func (m *MapLockString) Delete(k string) {
	m.Lock.Lock()
	delete(m.Data, k)
	defer m.Lock.Unlock()
}
