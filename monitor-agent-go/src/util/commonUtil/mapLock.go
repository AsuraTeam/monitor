package commonUtil

import "sync"

type MapLockString struct{
	Data map[string]string
	Lock sync.RWMutex
}

func (m *MapLockString) Set(k,v string){
	m.Lock.Lock()
	defer m.Lock.Unlock()
	if len(m.Data) < 1 {
		m.Data = make(map[string]string)
	}
	m.Data[k] = v
}

func (m *MapLockString) Delete(k string){
	m.Lock.Lock()
	defer m.Lock.Unlock()
	delete(m.Data, k)
}

func (m *MapLockString) Get(k string)(string,bool)  {
	m.Lock.RLock()
	defer m.Lock.RUnlock()
	if _,ok := m.Data[k];ok{
		return m.Data[k],true
	}
	return "",false
}
