variable "enable_blue_env" {
  description = "Enable blue environment"
  type        = bool
  default     = true
}

variable "enable_green_env" {
  description = "Enable green environment"
  type        = bool
  default     = true
}

variable "blue_version" {
  type = string
  default = "1.0"
}

variable "green_version" {
  type = string
  default = "1.1"
}

locals {
  traffic_dist_map = {
    blue = {
      blue  = 100
      green = 0
    }
    blue-90 = {
      blue  = 90
      green = 10
    }
    split = {
      blue  = 50
      green = 50
    }
    green-90 = {
      blue  = 10
      green = 90
    }
    green = {
      blue  = 0
      green = 100
    }
  }
}

variable "distribution" {
  type = string
  default = "split"
}