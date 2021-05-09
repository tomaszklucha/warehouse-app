resource "aws_launch_configuration" "green" {
  image_id = "ami-0a8e758f5e873d1c1"
  instance_type = "t2.micro"
  user_data = templatefile("${path.module}/init-script.sh", {
    version = var.green_version
    dbHost = aws_db_instance.app.address
    dbPort = aws_db_instance.app.port
    dbName = aws_db_instance.app.name
    dbUsername = aws_db_instance.app.username
    dbPassword = aws_db_instance.app.password
  })
  iam_instance_profile = "s3downloader"
}

resource "aws_autoscaling_group" "green" {
  max_size = var.enable_green_env ? 3 : 0
  min_size = 0
  desired_capacity = var.enable_green_env ? 1 : 0
  launch_configuration = aws_launch_configuration.green.name
  availability_zones = ["eu-west-1a", "eu-west-1b", "eu-west-1c"]
  health_check_type = "ELB"
  health_check_grace_period = 30
}

resource "aws_lb_target_group" "green" {
  name = "warehouse-app-green-tg-lb"
  port = 8080
  protocol = "HTTP"

  health_check {
    port = 8080
    path = "/actuator/health"
    protocol = "HTTP"
    timeout = 5
    interval = 10
  }

  vpc_id = "vpc-4ae59d2e"
}

resource "aws_autoscaling_attachment" "green" {
  autoscaling_group_name = aws_autoscaling_group.green.name
  alb_target_group_arn = aws_lb_target_group.green.arn
}
