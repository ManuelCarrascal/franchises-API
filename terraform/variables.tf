variable "aws_region" {
  description = "Región AWS"
  type        = string
  default     = "us-east-1"
}

variable "image_url" {
  description = "URI de la imagen en ECR"
  type        = string
}

variable "container_port" {
  description = "Puerto en el que corre la app dentro del contenedor"
  type        = number
  default     = 8080
}

variable "rds_endpoint" {
  description = "Endpoint de tu RDS"
  type        = string
}

variable "rds_port" {
  description = "Puerto de tu RDS"
  type        = number
  default     = 3306
}

variable "rds_db_name" {
  description = "Nombre de la base de datos"
  type        = string
}

variable "rds_user" {
  description = "Usuario maestro de la base de datos"
  type        = string
}

variable "rds_password" {
  description = "Contraseña del usuario de la base de datos"
  type        = string
  sensitive   = true
}

variable "vpc_id" {
  description = "ID de la VPC donde desplegarás ECS y ALB"
  type        = string
}

variable "subnet_ids" {
  description = "Lista de Subnet IDs (mínimo 2, preferiblemente privadas)"
  type        = list(string)
}

variable "rds_sg_id" {
  description = "Security Group ID que usa tu RDS"
  type        = string
}
