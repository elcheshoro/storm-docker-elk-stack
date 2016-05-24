Vagrant.configure("2") do |config|
  config.vm.provider "virtualbox" do |v|
    v.customize ["setextradata", :id, "VBoxInternal2/SharedFoldersEnableSymlinksCreate/v-root", "3"]
    v.memory = "4096"
  end
  config.vm.box = "ubuntu/trusty64"
  config.vm.box_check_update = false
  config.vm.network "private_network", ip: "10.10.20.10"
  config.vm.synced_folder ".", "/vagrant"
  config.vm.provision :shell, inline: "sudo apt-get update; sudo apt-get -y clean; sudo apt-get -y autoremove"
  config.vm.provision :docker
  config.vm.provision :docker_compose, compose_version: "1.6.2"
  config.vm.provision :docker_compose, yml: "/vagrant/docker-compose.yml", run: "always"
  config.vm.provision :docker_compose, yml: "/vagrant/word-topology-compose.yml", command_options: { up: ""}, run: "always"

end