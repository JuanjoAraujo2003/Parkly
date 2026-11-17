// Tipos que reflejan los DTOs del backend (com.parkingadmin.dto / entity).

export type SpaceStatus = 'AVAILABLE' | 'RESERVED' | 'OCCUPIED' | 'MAINTENANCE'

export type ActivityType = 'entry' | 'reservation' | 'payment' | 'exit'

export interface ParkingSpace {
  id: string
  zone: string
  status: SpaceStatus
  vehicle: string | null
  since: string | null
}

export interface Summary {
  total: number
  occupied: number
  reserved: number
  available: number
  maintenance: number
  occupancy: number
  revenue: number
  activeVehicles: number
}

export interface ActivityItem {
  vehicle: string
  action: string
  type: ActivityType
  occurredAt: string
}

export interface Dashboard {
  spaces: ParkingSpace[]
  summary: Summary
  activity: ActivityItem[]
}

export interface Tariff {
  name: string
  value: number
  unit: string
  active: boolean
}
