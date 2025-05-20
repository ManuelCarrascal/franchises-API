output "alb_dns_name" {
  description = "URL pública de tu aplicación"
  value       = aws_lb.app.dns_name
}

output "ecs_cluster_name" {
  description = "Nombre del ECS Cluster"
  value       = aws_ecs_cluster.franchise.name
}
