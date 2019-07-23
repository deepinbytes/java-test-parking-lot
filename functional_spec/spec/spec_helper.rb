require "bundler/setup"
require "pty"
require "timeout"

RSpec.configure do |config|
  # Enable flags like --only-failures and --next-failure
  config.example_status_persistence_file_path = ".rspec_status"

  # Disable RSpec exposing methods globally on `Module` and `main`
  config.disable_monkey_patching!

  config.expect_with :rspec do |c|
    c.syntax = :expect
  end
end

def run_command(pty, command)
  stdout, stdin, pid = pty
  stdin.puts command
  sleep(0.6)
  stdout.flush
end


def fetch_stdout(pty)
  stdout, stdin, pid = pty
  res = []
  while true
    begin
      Timeout::timeout 0.7 do
        res << stdout.readline
        stdout.sync
      end
    rescue EOFError, Errno::EIO, Timeout::Error
      break
    end
  end

  return res.join('').gsub(/\r/,'')
end
